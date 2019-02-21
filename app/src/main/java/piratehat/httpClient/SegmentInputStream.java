package piratehat.httpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class SegmentInputStream extends InputStream {
    private InputStream mInputStream;
    private HashMap<String, String> mHeaders;
    private boolean mChunked;
    private boolean mIsData;
    private boolean mEnd; //读取到末尾的标志 即读取到长度为 0
    private long mReadLength = 0L;//当前读取到的长度
    private long mSegmentLength = -1L; //分段时，每一段的长度
    public final boolean DEBUG = true;


    public SegmentInputStream(InputStream inputStream) throws IOException {
        mInputStream = inputStream;
        mHeaders = new HashMap<>();
        mChunked = false;
        mIsData = false;
        mEnd = false;
        parseHeaders();
    }

    public HashMap<String, String> getHeaders() {
        return mHeaders;
    }

    @Override
    public int read() throws IOException {

        return !mChunked ? mInputStream.read() : readChunked();
    }

    private int readChunked() throws IOException {
        if (mEnd) {
            return -1;
        }
        int byteCode;
        if (mIsData) {
            byteCode = mInputStream.read();
            mReadLength++;

            if (mReadLength == mSegmentLength) {
                mIsData = false;
                mReadLength = 0L;
                mSegmentLength = -1L;
            }
        } // << 数据的部分读取完毕
        else {
            int endTag = 0;//回车字符标识  一个 /n/r 就是一个回车
            byte[] buffer = new byte[1];
            ArrayList<Byte> bytes = new ArrayList<>();

            while ((byteCode = mInputStream.read()) != -1) {
                buffer[0] = (byte) byteCode;// 因为read(x,x,x)
                // 最后会调用read 所以是一个递归，会栈溢出
                if (buffer[0] != '\r' && buffer[0] != '\n') {
                    bytes.add(buffer[0]);
                    endTag = 0;
                } else {/* (buffer[0] == '\n' || buffer[0] == '\r')*/
                    endTag++;
                    if (endTag == 2 && bytes.size() != 0) {//四个字符就是有两个回车符，响应头就终止
                        byte[] resultByte = new byte[bytes.size()];
                        for (int i = 0; i < resultByte.length; i++) {
                            resultByte[i] = bytes.get(i);
                        }
                        String resultStr = new String(resultByte);
                        mSegmentLength = Integer.parseInt(resultStr.trim(), 16);
                        mEnd = mSegmentLength == 0;
                        mIsData = true;
                        break;
                    }

                }
            }//每次处理完成 长度数字后 都 要返回一个 data
            if (mIsData) {
                if (mEnd) {
                    return -1;
                }
                byteCode = mInputStream.read();
                mReadLength++;

                if (mReadLength == mSegmentLength) {
                    mIsData = false;
                    mReadLength = 0L;
                    mSegmentLength = -1L;
                }
            }
        }// << 分段的长度读取完毕

        return byteCode;
    }

    private void parseHeaders() throws IOException {
        if (mInputStream == null) {
            return;
        }
        int enterCount = 0;//回车字符标识  一个 /n/r 就是一个回车
        byte[] buffer = new byte[1];
        ArrayList<Byte> bytes = new ArrayList<>();
        while (read(buffer, 0, 1) != -1) { //
            bytes.add(buffer[0]);
            if (buffer[0] == '\n' || buffer[0] == '\r') {
                enterCount++;
                if (enterCount == 4) { //四个字符就是有两个回车符，响应头就终止
                    break;
                }
            } else {
                enterCount = 0;
            }
        }

        byte[] resultByte = new byte[bytes.size()];
        for (int i = 0; i < resultByte.length; i++) {
            resultByte[i] = bytes.get(i);
        }
        String resultStr = new String(resultByte);


        String[] headerLines = resultStr.split("\r\n");
        for (String headerLine : headerLines) {
            String[] header = headerLine.split(": ");
            if (header.length == 1) { //HTTP/1.1 200 OK 响应行只有一句
                mHeaders.put("", header[0].trim());
            } else {
                mHeaders.put(header[0].trim(), header[1].trim());

            }
        }
        mChunked = mHeaders.containsValue("chunked") || mHeaders.containsValue("CHUNKED");

        if (DEBUG) {
            System.out.println(mHeaders);
        }
    }


}

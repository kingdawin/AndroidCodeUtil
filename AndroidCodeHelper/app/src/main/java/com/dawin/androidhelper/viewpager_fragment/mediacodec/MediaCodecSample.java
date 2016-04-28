package com.dawin.androidhelper.viewpager_fragment.mediacodec;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 音频编解码代码示例
 * Created by admin on 2016/4/28.
 */
public class MediaCodecSample {

    byte[] decodeData = new byte[1024 * 1024 * 2];// 2MB
    MediaCodec mMediaCodec;
    MediaExtractor mediaExtractor;
    MediaFormat mMediaFormat;
    final int TIMEOUT_US = 1000;
    MediaCodec.BufferInfo info;
    boolean sawOutputEOS = false;
    boolean sawInputEOS = false;
    ByteBuffer[] codecInputBuffers;
    ByteBuffer[] codecOutputBuffers;

    /**
     * 解码音频文件，返回最后解码的数据
     *
     * @param url
     * @return
     */
    public byte[] decode(String url)
    {

        try
        {
            mediaExtractor.setDataSource(url);
        } catch (IOException e)
        {
        }
        mMediaFormat = mediaExtractor.getTrackFormat(0);
        String mime = mMediaFormat.getString(MediaFormat.KEY_MIME);
        try
        {
            mMediaCodec = MediaCodec.createDecoderByType(mime);
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mMediaCodec.configure(mMediaFormat, null, null, 0);
        mMediaCodec.start();

        codecInputBuffers = mMediaCodec.getInputBuffers();
        codecOutputBuffers = mMediaCodec.getOutputBuffers();

        info = new MediaCodec.BufferInfo();
        mediaExtractor.selectTrack(0);
        input();
        output();
        return decodeData;
    }

    private void output()
    {
        final int res = mMediaCodec.dequeueOutputBuffer(info, TIMEOUT_US);
        if (res >= 0)
        {
            int outputBufIndex = res;
            ByteBuffer buf = codecOutputBuffers[outputBufIndex];

            final byte[] chunk = new byte[info.size];
            buf.get(chunk); // Read the buffer all at once
            buf.clear(); // ** MUST DO!!! OTHERWISE THE NEXT TIME YOU GET THIS
            // SAME BUFFER BAD THINGS WILL HAPPEN

            if (chunk.length > 0)
            {
                System.arraycopy(chunk, 0, decodeData, 0, chunk.length);
                // mAudioTrack.write(chunk, 0, chunk.length);
            }
            mMediaCodec.releaseOutputBuffer(outputBufIndex, false /* render */);

            if ((info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0)
            {
                sawOutputEOS = true;
            }
        } else if (res == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED)
        {
            codecOutputBuffers = mMediaCodec.getOutputBuffers();
        } else if (res == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED)
        {
            final MediaFormat oformat = mMediaCodec.getOutputFormat();
            // Log.d(LOG_TAG, "Output format has changed to " + oformat);
            // mAudioTrack.setPlaybackRate(oformat.getInteger(MediaFormat.KEY_SAMPLE_RATE));
        }

    }

    private void input()
    {

        int inputBufIndex = mMediaCodec.dequeueInputBuffer(TIMEOUT_US);

        if (inputBufIndex >= 0)
        {
            ByteBuffer dstBuf = codecInputBuffers[inputBufIndex];

            int sampleSize = mediaExtractor.readSampleData(dstBuf, 0);
            // Log.i(LOG_TAG, "sampleSize : "+sampleSize);
            long presentationTimeUs = 0;
            if (sampleSize < 0)
            {
                // .Log.i(LOG_TAG, "Saw input end of stream!");
                sawInputEOS = true;
                sampleSize = 0;
            } else
            {
                presentationTimeUs = mediaExtractor.getSampleTime();
                // Log.i(LOG_TAG, "presentationTimeUs "+presentationTimeUs);
            }

            mMediaCodec.queueInputBuffer(inputBufIndex,
                    0, // offset
                    sampleSize, presentationTimeUs,
                    sawInputEOS ? MediaCodec.BUFFER_FLAG_END_OF_STREAM : 0);
            if (!sawInputEOS)
            {
                // Log.i(LOG_TAG, "extractor.advance()");
                mediaExtractor.advance();

            }
        }

    }
}

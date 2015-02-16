package com.parentcalendar.services.utility

import java.util.zip.DeflaterOutputStream
import java.util.zip.InflaterInputStream

public enum StringCompressor {

    public static byte[] compress(String text) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        try {
            OutputStream out = new DeflaterOutputStream(baos)
            out.write(text.getBytes("UTF-8"))
            out.close()
        } catch (IOException e) {
            throw new AssertionError(e)
        }
        return baos.toByteArray()
    }

    public static String decompress(byte[] bytes) {
        InputStream data = new InflaterInputStream(new ByteArrayInputStream(bytes))
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        try {
            byte[] buffer = new byte[8192]
            int len
            while((len = data.read(buffer))>0)
            baos.write(buffer, 0, len)
            return new String(baos.toByteArray(), "UTF-8")
        } catch (IOException e) {
            throw new AssertionError(e)
        }
    }
}
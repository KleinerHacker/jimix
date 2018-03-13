package org.pcsoft.app.jimix.core.tooling;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class RecentFileManagerTest {
    private static final File FILE = new File(".recent");

    @Before
    public void init() throws IOException {
        if (FILE.exists())
            FileUtils.forceDelete(FILE);
    }

    @After
    public void done() throws IOException {
        if (FILE.exists())
            FileUtils.forceDelete(FILE);

        RecentFileManager.getInstance().clearFiles();
    }

    @Test
    public void test() throws IOException {
        RecentFileManager.getInstance().addFile(new File("C:\\"));
        RecentFileManager.getInstance().addFile(new File("D:\\"));

        Assert.assertEquals("D:\\" + SystemUtils.LINE_SEPARATOR + "C:\\" + SystemUtils.LINE_SEPARATOR, FileUtils.readFileToString(FILE));
    }

    @Test
    public void testMax() throws IOException {
        RecentFileManager.getInstance().addFile(new File("C:\\"));
        RecentFileManager.getInstance().addFile(new File("D:\\"));
        RecentFileManager.getInstance().addFile(new File("E:\\"));
        RecentFileManager.getInstance().addFile(new File("F:\\"));
        RecentFileManager.getInstance().addFile(new File("G:\\"));
        RecentFileManager.getInstance().addFile(new File("H:\\"));
        RecentFileManager.getInstance().addFile(new File("I:\\"));
        RecentFileManager.getInstance().addFile(new File("J:\\"));
        RecentFileManager.getInstance().addFile(new File("K:\\"));
        RecentFileManager.getInstance().addFile(new File("L:\\"));
        RecentFileManager.getInstance().addFile(new File("M:\\"));

        Assert.assertEquals("M:\\" + SystemUtils.LINE_SEPARATOR + "L:\\" + SystemUtils.LINE_SEPARATOR +
                "K:\\" + SystemUtils.LINE_SEPARATOR + "J:\\" + SystemUtils.LINE_SEPARATOR + "I:\\" + SystemUtils.LINE_SEPARATOR +
                "H:\\" + SystemUtils.LINE_SEPARATOR + "G:\\" + SystemUtils.LINE_SEPARATOR + "F:\\" + SystemUtils.LINE_SEPARATOR +
                "E:\\" + SystemUtils.LINE_SEPARATOR + "D:\\" + SystemUtils.LINE_SEPARATOR, FileUtils.readFileToString(FILE));
    }

    @Test
    public void testResort() throws IOException {
        RecentFileManager.getInstance().addFile(new File("C:\\"));
        RecentFileManager.getInstance().addFile(new File("D:\\"));

        Assert.assertEquals("D:\\" + SystemUtils.LINE_SEPARATOR + "C:\\" + SystemUtils.LINE_SEPARATOR, FileUtils.readFileToString(FILE));

        RecentFileManager.getInstance().addFile(new File("C:\\"));

        Assert.assertEquals("C:\\" + SystemUtils.LINE_SEPARATOR + "D:\\" + SystemUtils.LINE_SEPARATOR, FileUtils.readFileToString(FILE));
    }

}
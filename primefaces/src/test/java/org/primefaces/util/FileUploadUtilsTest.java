/*
 * The MIT License
 *
 * Copyright (c) 2009-2024 PrimeTek Informatics
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.primefaces.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.context.PrimeApplicationContext;
import org.primefaces.model.file.UploadedFile;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileUploadUtilsTest {

    private FileUpload fileUpload;
    private InputStream inputStream;
    private PrimeApplicationContext appContext;

    @BeforeEach
    void setup() {
        fileUpload = mock(FileUpload.class);
        inputStream = mock(InputStream.class);
        FacesContext context = mock(FacesContext.class);
        Application app = mock(Application.class);
        when(context.getApplication()).thenReturn(app);
        ExternalContext externalContext = mock(ExternalContext.class);
        when(context.getExternalContext()).thenReturn(externalContext);
        appContext = new PrimeApplicationContext(context);
    }

    @AfterEach
    void teardown() {
        inputStream = null;
        fileUpload = null;
    }

    private UploadedFile createFile(String filename, String contentType, InputStream stream) {
        UploadedFile file = Mockito.mock(UploadedFile.class);
        when(file.getFileName()).thenReturn(filename);
        when(file.getContentType()).thenReturn(contentType);
        try {
            when(file.getInputStream()).thenReturn(stream);
        }
        catch (IOException e) {
        }
        return file;
    }

    @Test
    void isValidType_NameCheck() {
        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("test.png", "image/png", inputStream), null, null));

        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("test.PNG", "image/png", inputStream), "/\\.(gif|png|jpe?g)$/i", null));
        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("test.jpeg", "image/jpeg", inputStream), "/\\.(gif|png|jpe?g)$/i", null));
        assertFalse(FileUploadUtils.isValidType(appContext,
                createFile("test.bmp", "image/bitmap", inputStream), "/\\.(gif|png|jpe?g)$/i", null));
    }

    @Test
    void isValidType_MimeTypeCheck() {
        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("test.PNG", "image/png", inputStream), "/image/g", null));
        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("test.jpeg", "image/png", inputStream), "/image/g", null));
        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("test.bmp", "image/bitmap", inputStream), "/image/g", null));
        assertFalse(FileUploadUtils.isValidType(appContext,
                createFile("adobe.pdf", "application/pdf", inputStream), "/image/g", null));
    }

    @Test
    void isValidType_InvalidRegex() {
        // all of these should be true when the regex is not valid
        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("test.PNG", "image/png", inputStream), "x", null));
        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("test.jpeg", "image/png", inputStream), "x", null));
        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("test.bmp", "image/bitmap", inputStream), "x", null));
        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("adobe.pdf", "application/pdf", inputStream), "x", null));
    }

    @Test
    void isValidTypeContentTypeCheck() throws IOException {
        InputStream tif = new ByteArrayInputStream(new byte[]{0x49, 0x49, 0x2A, 0x00});
        InputStream mp4 = new ByteArrayInputStream(new byte[]{0x00, 0x00, 0x00, 0x00, 0x66, 0x74, 0x79, 0x70, 0x69, 0x73, 0x6F, 0x6D});
        InputStream png = new ByteArrayInputStream(IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.png")));
        InputStream bmp = new ByteArrayInputStream(new byte[]{0x42, 0x4D});
        InputStream gif = new ByteArrayInputStream(new byte[]{0x47, 0x49, 0x46, 0x38, 0x39, 0x61});
        InputStream exe = new ByteArrayInputStream(new byte[]{0x4D, 0x5A});

        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("test.TIF", "image/tif", tif), null, null));
        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("test.mp4", "application/music", mp4), null, null));

        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("test.png", "image/png", png), null, ""));

        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("test.png", "image/png", png), null, ".png,.bmp"));
        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("test.bmp", "image/bmp", bmp), null, ".png,.bmp"));
        assertFalse(FileUploadUtils.isValidType(appContext,
                createFile("test.tif", "image/tif", tif), null, ".png,.bmp"));

        assertFalse(FileUploadUtils.isValidType(appContext,
                createFile("test.png", "image/png", png), null, "video/*"));
        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("test.mp4", "application/music", mp4), null, "video/*"));

        assertFalse(FileUploadUtils.isValidType(appContext,
                createFile("test.png", "image/png", png), null, "image/tiff"));

        //Tampered - Apache Tika or Mime Type must be in the classpath for this to work
        assertFalse(FileUploadUtils.isValidType(appContext,
                createFile("test.gif", "image/gif", exe), null, "image/gif"));
        assertTrue(FileUploadUtils.isValidType(appContext,
                createFile("test.png", "image/png", gif), null, "image/gif"));
    }

    @Test
    void requireValidFilePath_AbsoluteFile() throws URISyntaxException {
        // Arrange
        String absolutePath = Paths.get(FileUploadUtilsTest.class.getResource("/test.png").toURI())
                .toFile().getAbsolutePath();

        // Act
        String result = FileUploadUtils.requireValidFilePath(absolutePath, false);

        //Assert
        assertTrue(result.endsWith("test.png"));
    }

    @Test
    void requireValidFilePath_AbsoluteFileWithWindowsSpaces() {
        // Unix systems can start with / but Windows cannot
        String os = System.getProperty("os.name").toLowerCase();

        // Arrange
        String absolutePath;

        // Act
        if (os.contains("win")) {
            absolutePath = "D:/Development/OS%20Projects/primefaces/primefaces/target/test-classes/test.png";
            String result = FileUploadUtils.requireValidFilePath(absolutePath, false);
            assertTrue(result.endsWith("test.png"));
        }
        else {
            absolutePath = "/Development/OS%20Projects/primefaces/primefaces/target/test-classes/test.png";
            String result = FileUploadUtils.requireValidFilePath(absolutePath, false);
            assertTrue(result.endsWith("test.png"));
        }
    }

    @Test
    void requireValidFilePath_PathTraversal() {
        // Arrange
        String relativePath = "../../test.png";

        // Act
        try {
            FileUploadUtils.requireValidFilePath(relativePath, false);
            fail("File was absolute path and should have failed");
        }
        catch (ValidatorException e) {
            // Assert
            assertEquals("Invalid directory, name does not match the canonical path.", e.getFacesMessage().getDetail());
        }
    }

    @Test
    void requireValidFilePath_NotNull() {
        // Arrange
        String invalidFileName = null;

        // Act
        ValidatorException ex = assertThrows(ValidatorException.class, () -> FileUploadUtils.requireValidFilePath(invalidFileName, false));

        // Assert
        assertEquals("Path can not be the empty string or null", ex.getFacesMessage().getDetail());
    }

    @Test
    void requireValidFilePath_NotBlank() {
        // Arrange
        String invalidFileName = " ";

        // Act
        ValidatorException ex = assertThrows(ValidatorException.class, () -> FileUploadUtils.requireValidFilePath(invalidFileName, false));

        // Assert
        assertEquals("Path can not be the empty string or null", ex.getFacesMessage().getDetail());
    }

    @Test
    void requireValidFilePath_InvalidCharacters() {
        // Arrange
        String os = System.getProperty("os.name").toLowerCase();
        String invalidFileName = "invalid\\path*";

        // Act
        ValidatorException ex = assertThrows(ValidatorException.class, () -> FileUploadUtils.requireValidFilePath(invalidFileName, false));

        // Assert
        if (os.contains("win")) {
            assertEquals("Invalid path: (invalid\\path*) contains invalid character: *", ex.getFacesMessage().getDetail());
        }
        else {
            assertEquals("Invalid directory, \"invalid\\path*\" is not valid.", ex.getFacesMessage().getDetail());
        }
    }

    @Test
    void convertJavaScriptRegex_Normal() {
        // Arrange
        String jsRegex = "/(\\.|\\/)(gif|jpe?g|png)$/";

        // Act
        String result = FileUploadUtils.convertJavaScriptRegex(jsRegex);

        // Assert
        assertEquals("(\\.|\\/)(gif|jpe?g|png)$", result);
    }

    @Test
    void convertJavaScriptRegex_CaseInsensitive() {
        // Arrange
        String jsRegex = "/\\.(gif|png|jpe?g)$/i";

        // Act
        String result = FileUploadUtils.convertJavaScriptRegex(jsRegex);

        // Assert
        assertEquals("\\.(gif|png|jpe?g)$", result);
    }

    @Test
    void convertJavaScriptRegex_Short() {
        // Arrange
        String jsRegex = "/x/";

        // Act
        String result = FileUploadUtils.convertJavaScriptRegex(jsRegex);

        // Assert
        assertEquals("x", result);
    }

    @Test
    void convertJavaScriptRegex_NotRegex() {
        // Arrange
        String jsRegex = "x";

        // Act
        String result = FileUploadUtils.convertJavaScriptRegex(jsRegex);

        // Assert
        assertEquals("", result);
    }

    @Test
    void getValidFilename_GitHub8359() {
        // Arrange
        String filename = "~myfile.txt";

        // Act
        String result = FileUploadUtils.requireValidFilename(filename);

        // Assert
        assertEquals(filename, result);
    }

    @Test
    void requireValidFilename_Valid() {
        // valid files
        assertEquals("someFileWithUmlautsßöäü.txt", FileUploadUtils.requireValidFilename("someFileWithUmlautsßöäü.txt"));
        assertEquals("StringFinder.exe", FileUploadUtils.requireValidFilename("StringFinder.exe"));
        assertEquals("January.xlsx", FileUploadUtils.requireValidFilename("January.xlsx"));
        assertEquals("TravelBrochure.pdf", FileUploadUtils.requireValidFilename("TravelBrochure.pdf"));
    }

    @Test
    void requireValidFilename_Linux() {
        try (MockedStatic<FileUploadUtils> fileUploadUtilsMockedStatic = Mockito.mockStatic(FileUploadUtils.class, Mockito.CALLS_REAL_METHODS)) {
            fileUploadUtilsMockedStatic.when(FileUploadUtils::isSystemWindows).thenReturn(false);

            // Check each invalid character individually
            String[] invalidCharacters = {"/", "\0", ":", "*", "?", "\"", "<", ">", "|"};
            for (String character : invalidCharacters) {
                String invalidFileName = "filename_with_" + character;
                assertThrows(FacesException.class, () -> FileUploadUtils.requireValidFilename(invalidFileName));
            }
        }
    }

    @Test
    void requireValidFilename_Windows() {
        // invalid files
        try (MockedStatic<FileUploadUtils> fileUploadUtilsMockedStatic = Mockito.mockStatic(FileUploadUtils.class, Mockito.CALLS_REAL_METHODS)) {
            fileUploadUtilsMockedStatic.when(FileUploadUtils::isSystemWindows).thenReturn(true);

            // Check each invalid character individually
            String[] invalidCharacters = {"/", "\\\\", ":", "*", "?", "\"", "<", ">", "|"};
            for (String character : invalidCharacters) {
                String invalidFileName = "filename_with_" + character;
                assertThrows(FacesException.class, () -> FileUploadUtils.requireValidFilename(invalidFileName));
            }

            // Add reserved device names as invalid filenames
            String[] reservedDeviceNames = {"CON", "PRN", "AUX", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2",
                "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9" };
            for (String deviceName : reservedDeviceNames) {
                assertThrows(FacesException.class, () -> FileUploadUtils.requireValidFilename(deviceName));
            }
        }
    }

    @Test
    void requireValidFilename_EncodedChars() {
        try (MockedStatic<FileUploadUtils> fileUploadUtilsMockedStatic = Mockito.mockStatic(FileUploadUtils.class, Mockito.CALLS_REAL_METHODS)) {
            fileUploadUtilsMockedStatic.when(FileUploadUtils::isSystemWindows).thenReturn(false);

            // Check each invalid character individually
            String[] invalidCharacters = {"%20", "%21"};
            for (String ch : invalidCharacters) {
                String invalidFileName = "filename_with_" + ch;
                ValidatorException ex = assertThrows(ValidatorException.class, () -> FileUploadUtils.requireValidFilename(invalidFileName));
                assertEquals("Invalid filename: (filename_with_" + ch + ") contains invalid character: " + ch, ex.getFacesMessage().getDetail());
            }
        }
    }

    @Test
    void requireValidFilename_NotNull() {
        // Arrange
        String invalidFileName = null;

        // Act
        ValidatorException ex = assertThrows(ValidatorException.class, () -> FileUploadUtils.requireValidFilename(invalidFileName));

        // Assert
        assertEquals("Filename cannot be empty or null", ex.getFacesMessage().getDetail());
    }

    @Test
    void requireValidFilename_NotBlank() {
        // Arrange
        String invalidFileName = " ";

        // Act
        ValidatorException ex = assertThrows(ValidatorException.class, () -> FileUploadUtils.requireValidFilename(invalidFileName));

        // Assert
        assertEquals("Filename cannot be empty or null", ex.getFacesMessage().getDetail());
    }

    @Test
    void requireValidFilename_InvalidCharacters() {
        // Arrange
        String invalidFileName = "file:*";
        String os = System.getProperty("os.name").toLowerCase();

        // Act
        ValidatorException ex = assertThrows(ValidatorException.class, () -> FileUploadUtils.requireValidFilename(invalidFileName));

        // Assert
        if (os.contains("win")) {
            assertEquals("Invalid filename: (file:*) contains invalid character: :", ex.getFacesMessage().getDetail());
        }
        else {
            assertEquals("Invalid Linux filename: file:*", ex.getFacesMessage().getDetail());
        }
    }

    @Test
    void formatBytes() {
        assertEquals("1000 Bytes", FileUploadUtils.formatBytes(1000L, Locale.US));
        assertEquals("1.0 KB", FileUploadUtils.formatBytes(1025L, Locale.US));
    }

    @Test
    void requireValidFilename() {
        assertDoesNotThrow(() -> FileUploadUtils.requireValidFilename("someFileWithUmlautsßöäü.txt"));
        // validate fileName which contains /, which is invalid in a filename
        assertThrows(FacesException.class, () -> FileUploadUtils.requireValidFilename("someFil/eWithUmlautsßöäü.txt"));
        // validate fileName which contains <, which is invalid in a filename
        assertThrows(FacesException.class, () -> FileUploadUtils.requireValidFilename("someFil<eWithUmlautsßöäü.txt"));
    }

}

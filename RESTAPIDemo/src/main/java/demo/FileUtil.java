package demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
	/**************
	 * 
	 * @param absoluteFileName
	 * @return Content of file as String
	 * @throws IOException
	 */
	public static String getFileContent(String absoluteFileName) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(absoluteFileName));
		return new String(encoded, StandardCharsets.UTF_8);
	}

	public static void createTextFile(String fileNameToCreate, String content) throws IOException {
		File fileObj = new File(fileNameToCreate);
		if (fileObj.createNewFile()) {
			System.out.println("File Created: " + fileObj.getName());
		} else {
			System.out.println("File already exists");
		}

		FileWriter fileWriter = new FileWriter(fileNameToCreate);
		fileWriter.write(content);
		fileWriter.close();
		System.out.println("Successfully wrote to the file " + fileObj.getName());
	}

	public static void deleteFile(String fileNameToCreate) throws IOException {
		File fileObj = new File(fileNameToCreate);
		if (fileObj.delete()) {
			System.out.println("File Deleted: " + fileObj.getName());
		} else {
			System.out.println("Failed to delete the file " + fileObj.getName());
		}
	}

}

package musicTheatre;

import java.nio.file.Paths;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class QRCodeGenerator {

	public static void base() throws Exception {
		
		String data = "http://www.infybuzz.com";
		String path = "D:\\QR-Code\\infybuzz.jpg";
		
		BitMatrix matrix = new MultiFormatWriter()
				.encode(data, BarcodeFormat.QR_CODE, 500, 500);
		
		MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));

	}

}
package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.runners.model.FrameworkField;
import primitives.*;
import renderer.*;
class ImageWriterTests {
	
	
	@Test
	void testWriteToImage() {

		ImageWriter image = new ImageWriter("test", 800, 500);
		Color color1 = new Color(255.0, 255.0, 255.0);
		Color color2 = new Color(170.0, 20.0, 100.0);
		
		for (int i = 0; i < 800; i++)
		{
			for (int j = 0; j < 500; j++)
			{
				if (j % 50 == 0 || i %50 == 0)
				    image.writePixel(i, j, color1);
				else 
					image.writePixel(i, j, color2);	
			}
		}
		image.writeToImage();
	}


}

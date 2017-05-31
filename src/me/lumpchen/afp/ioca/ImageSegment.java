package me.lumpchen.afp.ioca;

import java.awt.image.BufferedImage;
import java.io.IOException;

import me.lumpchen.afp.AFPInputStream;

public class ImageSegment {
	
	public static final int Begin = 0x70;
	public static final int End = 0x71;
	
	public static final int IDE_Size_ID = 0x96;
	public static final int IDE_LUT_ID = 0x97;
	public static final int Band_Image_ID = 0x98;
	
	public static final int Double_Byte_ID_0 = 0xFE;
	
	private ImageContent imageContent;
	private byte[] name;
	
	public ImageSegment() {
	}
	
	public void setImageContent(ImageContent imageContent) {
		this.imageContent = imageContent;
	}
	
	public BufferedImage getJavaImage() {
		return this.imageContent.getJavaImage();
	}
	
	public void read(AFPInputStream in) throws IOException {
		int id = in.readCode();
		if (id != ImageSegment.Begin) {
			throw new IOException("Invalid Image Segment begin mark(0x70): " + id);
		}
		int length = in.readUBin(1);
		if (length > 0) {
			this.name = in.readBytes(4);
		}
		
		ImageContent imageContent = new ImageContent();
		imageContent.read(in);
		this.setImageContent(imageContent);
		
		id = in.readCode();
		if (id != ImageSegment.End) {
//			throw new IOException("Invalid Image Segment end mark(0x71): " + id);
		}
		length = in.readUBin(1);
		if (length > 0) {
			in.readBytes(length);
		}
	}
}

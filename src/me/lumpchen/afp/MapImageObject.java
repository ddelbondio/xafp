package me.lumpchen.afp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.lumpchen.afp.sf.StructureField;
import me.lumpchen.afp.sf.triplet.Triplet;

public class MapImageObject extends AFPObject {

	private int RGLength;
	private List<Triplet> triplets;
	
	public MapImageObject(StructureField structField) throws IOException {
		super(structField);
		this.parseData(this.structField.getData());
	}
	
	private void parseData(byte[] data) throws IOException {
		AFPInputStream in = new AFPInputStream(data);
		try {
			this.RGLength = in.readUBin(2);
			while (in.remain() > 0) {
				Triplet triplet = Triplet.readTriple(in);
				if (this.triplets == null) {
					this.triplets = new ArrayList<Triplet>();
				}
				this.triplets.add(triplet);
			}
			
		} finally {
			in.close();
		}
	}
}
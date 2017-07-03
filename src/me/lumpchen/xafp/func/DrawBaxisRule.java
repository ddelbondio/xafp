package me.lumpchen.xafp.func;

import java.io.IOException;

import me.lumpchen.xafp.AFPInputStream;
import me.lumpchen.xafp.ActiveEnvironmentGroup;
import me.lumpchen.xafp.render.AFPGraphics;
import me.lumpchen.xafp.render.ResourceManager;

public class DrawBaxisRule extends Function {

	private int RLENGTH;
	private int RWIDTH;
	
	public DrawBaxisRule() {
		this.type = PTX_DIR;
	}

	@Override
	public void render(ActiveEnvironmentGroup aeg, AFPGraphics graphics, ResourceManager resourceManager) {
		float len = (float) aeg.unit2Point(this.RLENGTH);
		
		int dw = this.RWIDTH >> 8; // skip the third byte for fraction
		float w = (float) aeg.unit2Point(dw);
		graphics.setLineWidth(w);
		
		graphics.drawRule(0, 0, 0, len, false);
	}
	
	@Override
	void readFunction(AFPInputStream in) throws IOException {
		this.RLENGTH = in.readSBin(2);
		this.remain -= 2;
		
		if (this.remain > 0) {
			this.RWIDTH = in.readSBin(3);
			this.remain -= 3;
		}
	}
	
	@Override
	public String getCommandString() {
		return "DBR";
	}
	
	@Override
	public String getCommandDesc() {
		return "Draw B-axis Rule";
	}

}
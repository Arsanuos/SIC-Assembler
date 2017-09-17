package statments.data;

import resources.Resources;

public class Data implements DataIF {

	private Resources resources;
	private Resb resb;
	private Byte byteData;
	private Resw resw;
	private Word word;

	public Data(Resources resources) {
		// TODO Auto-generated constructor stub
		this.resources = resources;
		resb = new Resb();
		byteData = new Byte();
		resw = new Resw();
		word = new Word();
	}

	@Override
	public boolean isData(String line) {
		// TODO Auto-generated method stub
		if (resb.isResb(line) || byteData.isByte(line) || resw.isResw(line) || word.isWord(line)) {
			return true;
		}
		return false;
	}

	@Override
	public int eval(String line) {
		// TODO Auto-generated method stub
		int out = 0;
		if (resb.isResb(line)) {
			out = resb.eval(line);
		} else if (byteData.isByte(line)) {
			out = byteData.eval(line);
		} else if (resw.isResw(line)) {
			out = resw.eval(line);
		} else if (word.isWord(line)) {
			out = word.eval(line);
		}
		return out;
	}

}

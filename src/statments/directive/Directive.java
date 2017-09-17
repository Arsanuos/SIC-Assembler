package statments.directive;

import resources.Resources;

public class Directive implements DirectiveIF {

	private Start start;
	private End end;
	private Resources resources;
	private Ltorg ltorg;
	private ORG org;
	private EQU equ;
	private boolean wasLtorg;

	public Directive(Resources resources) {
		// TODO Auto-generated constructor stub
		wasLtorg = false;
		start = new Start();
		org = new ORG(resources);
		end = new End();
		ltorg = new Ltorg();
		equ = new EQU(resources);
		this.resources = resources;
	}

	@Override
	public boolean isDirective(String line) {
		// TODO Auto-generated method stub
		// start , end,
		if (start.isStart(line) || end.isEnd(line)
				|| ltorg.isLtorg(line) || org.isOrg(line) || equ.isEQU(line)) {
			return true;
		}
		return false;
	}

	@Override
	public int eval(String line) {
		// TODO Auto-generated method stub
		wasLtorg = false;
		if (start.isStart(line)) {
			return start.eval(line);
		} else if (end.isEnd(line)) {
			end.eval(line);
			return ltorg.eval(line, resources);
		} else if (ltorg.isLtorg(line)) {
			wasLtorg = true;
			return ltorg.eval(line, resources);
		} else if (org.isOrg(line)) {
			org.eval(line);
			return 0;
		} else if (equ.isEQU(line)) {
			equ.eval(line);
			return 0;
		} else {
			throw new RuntimeException(".Error in eval directive ");
		}
	}

	@Override
	public boolean isStart(String line) {
		// TODO Auto-generated method stub
		if (start.isStart(line)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isEnd(String line) {
		// TODO Auto-generated method stub
		if (end.isEnd(line)) {
			return true;
		}
		return false;
	}

	@Override
	public void setRepeatedStart() {
		// TODO Auto-generated method stub
		this.start.setRepeatedStart();
	}

	@Override
	public boolean wasLtorg() {
		// TODO Auto-generated method stub
		return this.wasLtorg;
	}

	@Override
	public void setLtorg() {
		// TODO Auto-generated method stub
		this.wasLtorg = false;
	}

	@Override
	public boolean isEQU(String line) {
		// TODO Auto-generated method stub
		return equ.isEQU(line);
	}

}

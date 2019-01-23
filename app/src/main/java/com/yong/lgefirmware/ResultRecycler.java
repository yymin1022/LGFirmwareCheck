package com.yong.lgefirmware;

public class ResultRecycler
{
	public String device;
	public String operator;
	public String chipset;
	public String update;
	
	public ResultRecycler(String device, String operator, String chipset, String update){
		this.device = device;
		this.operator = operator;
		this.chipset = chipset;
		this.update = update;
	}
}

package net.smartworks.model;

public class Filter {
	
	private String left;
	private String operator = "like";
	private String right;
	
	public Filter(String left, String operator, String right) {
		this.left = left;
		this.operator = operator;
		this.right = right;
	}
	
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
	
}

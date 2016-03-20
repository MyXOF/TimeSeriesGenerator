package xof.timeSeriesGenerator.utils;

public class NoNameType <T extends Comparable<T>> implements Comparable<T>{
	public T type;
	public NoNameType(T type){
		this.type = type;
	}
	@Override
	public int compareTo(T o) {
		return type.compareTo(o);
	}
	
	@Override
	public String toString(){
		return "";
	}
}

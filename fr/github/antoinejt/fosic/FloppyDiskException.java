package fr.github.antoinejt.fosic;

public class FloppyDiskException extends Throwable {
	public FloppyDiskException() {
		try {
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public FloppyDiskException(String ex) {
		try {
			throw new Exception(ex);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package forms;

import javax.validation.constraints.NotNull;

public class FilterLessor {
	
	public FilterLessor(){
		super();
	}

	private int lessorId;
	
	@NotNull
	public int getLessorId() {
		return lessorId;
	}

	public void setLessorId(int lessorId) {
		this.lessorId = lessorId;
	}
	

}
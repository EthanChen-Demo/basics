package sqlBuilder.test;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class BaseVo implements Serializable {
	private static final long serialVersionUID = -2455172166170402359L;

	protected Timestamp dateCreate;//创建时间
	
	protected Timestamp dateUpdate;//修改时间
	
	protected Timestamp dateDelete;//删除时间
	
    public Timestamp getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Timestamp dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Timestamp getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Timestamp dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Timestamp getDateDelete() {
		return dateDelete;
	}

	public void setDateDelete(Timestamp dateDelete) {
		this.dateDelete = dateDelete;
	}
}


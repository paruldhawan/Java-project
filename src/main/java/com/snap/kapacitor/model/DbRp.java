package com.snap.kapacitor.model;

public class DbRp {
	private String db;
	private String rp;
	
	public DbRp(String db, String rp) {
		super();
		this.db = db;
		this.rp = rp;
	}
	
	

	public String getDb() {
		return db;
	}


	public void setDb(String db) {
		this.db = db;
	}


	public String getRp() {
		return rp;
	}


	public void setRp(String rp) {
		this.rp = rp;
	}



	@Override
	public String toString() {
		return db + "." + rp;
	}
	
	@Override
	public int hashCode() {
		String v = db+"."+rp;
		return v.hashCode();
		
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DbRp){
			DbRp dbrp = (DbRp) obj;
			return dbrp.db.equals(this.db) && dbrp.rp.equals(this.rp);
		}
		return false;
	}
}

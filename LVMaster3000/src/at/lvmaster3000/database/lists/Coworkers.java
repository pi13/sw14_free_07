package at.lvmaster3000.database.lists;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import at.lvmaster3000.database.objects.Coworker;

public class Coworkers {

	private List<Coworker> coworkers;
		
	public Coworkers() {
		this.coworkers = new ArrayList<Coworker>();
	}
	
	public void addCoworker(Coworker Coworker) {
		this.coworkers.add(Coworker);
	}
	
	public List<Coworker> getCoworkers() {
		return this.coworkers;
	}
	
	public void cursorToLectureList(Cursor cursor) {
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			this.coworkers.add(new Coworker().cursorToCoworker(cursor));
			cursor.moveToNext();
		}
	}
	
	public void printLectureList() {
		for(int i = 0; i < coworkers.size(); i++) {
			coworkers.get(i).printCoworker();
		}
	}

	public int nrOfCoworkers() {
		return this.coworkers.size();
	}

	public void deleteAllCoworkers() {
		this.coworkers.clear();		
	}

	public int nrOfObjects() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}

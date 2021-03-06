package at.lvmaster3000.database.lists;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import at.lvmaster3000.database.objects.Date;

public class Dates {
	private List<Date> dates;
	
	public Dates() {
		this.dates = new ArrayList<Date>();
	}
	
	public void addDate(Date date) {
		this.dates.add(date);
	}
	
	public List<Date> getDates() {
		return this.dates;
	}
	
	public void cursorToDateList(Cursor cursor) {
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			this.dates.add(new Date().cursorToDate(cursor));
			cursor.moveToNext();
		}
	}
	
	public void printDateList() {
		for(int i = 0; i < dates.size(); i++) {
			dates.get(i).printDate();
		}
	}

	public int nrOfDates() {
		return this.dates.size();
	}

	public void clear() {
		this.dates.clear();
	}

	public int nrOfObjects() {
		// TODO Auto-generated method stub
		return 0;
	}
}

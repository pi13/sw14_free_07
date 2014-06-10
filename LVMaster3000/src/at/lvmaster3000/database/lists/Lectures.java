package at.lvmaster3000.database.lists;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import at.lvmaster3000.database.objects.Lecture;

public class Lectures {
	private List<Lecture> lectures;
	
	public Lectures() {
		this.lectures = new ArrayList<Lecture>();
	}
	
	public void addLecture(Lecture lecture) {
		this.lectures.add(lecture);		
	}
	
	public List<Lecture> getLectures() {
		return this.lectures;
	}
	
	public void cursorToLectureList(Cursor cursor) {
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			this.lectures.add(new Lecture().cursorToLecture(cursor));
			cursor.moveToNext();
		}
	}
	
	public void printLectureList() {
		for(int i = 0; i < lectures.size(); i++) {
			lectures.get(i).printLecture();
		}
	}

	public void clear() {
		this.lectures.clear();
	}
}

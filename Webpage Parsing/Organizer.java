import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

class Student
{
	private String myName;
	private String mySchool;
	private int myScore;
	private String myDist;
	private String myPlace;
	
	public Student (String name, String school, int score, String dist, String place)
	{
		myName = name; mySchool=school; myScore=score;myDist = dist;myPlace=place;
	}
	
	public String getName() {return myName;}
	public String getSchool() {return mySchool;}
	public int getScore() {return myScore;}
	public String getDist() {return myDist;}
	public String getPlace() {return myPlace;}
}

public class Organizer
{
	private static char dist = 'D'; // change to 'R' for regionals

	private static int year = 2013; // change to current year

	//parse together the URL for accessing the UIL website
	private static String[] category = {"Calculator Applications", "Computer Science", "Mathematics", "Number Sense", "Science"};
	private static String one = "http://utdirect.utexas.edu/uil/vlcp_results.WBX?s_event_abbr="; //contestField
	private static String two = "&s_submit_sw=X&s_year=";
	private static String twohalf = "&s_conference="; //class
	private static String three = "A&s_level_id=";
	private static String threehalf = "&s_level_nbr="; // district
	private static String four = "&s_gender=&s_round=&s_dept=C&s_meet_abbr=SPM&s_area_zone=";
	private static String[] contestField = {"CAL", "CSC", "MTH", "NUM", "SCI"};
	private String urlcontents,chosenClass;
	private int chosen,j;

	
	public Organizer()
	{
		for (int frank=5; frank>=1; frank--)
		{
			 chosenClass = ""+frank;//JOptionPane.showInputDialog("Please enter classification \n(ex: 1 for 1A, 4 for AAAA)");
			for (int barney = 0; barney<5; barney++)
			{
					chosen = barney;//Integer.parseInt(chosenContest)-1;
					System.out.println(chosen);
		ArrayList studentList = new ArrayList();
		ArrayList schoolList = new ArrayList();
		for (j=1; j<=32; j++)
		{
		//System.getProperties().put( "proxySet", "true" );
		//System.getProperties().put( "proxyHost", "NISD_PROXY" );
		//System.getProperties().put( "proxyPort", "8080" );

			System.out.println("Trying District "+j+"-"+chosenClass+"A");
			String url =one + contestField[chosen]+two+year+twohalf+chosenClass+three+dist+threehalf+j+four;
			try
			{
				URL myURL = new URL(url);
				//System.out.println(url);
				URLConnection cc = myURL.openConnection();
				DataInputStream in = new DataInputStream(cc.getInputStream());
				int inputNums;
				while ((inputNums=in.read())!=-1)
				{
					urlcontents = urlcontents+(char)inputNums;
				}
				in.close();	
				cc = new URL("http://www.yahoo.com").openConnection();				
			}
			catch (Exception e)
			{
				System.out.println("Failed for district "+j+"-"+chosenClass+"A.");
				e.printStackTrace();
			}
			System.out.println("Processing District "+j+"-"+chosenClass+"A");
			process(urlcontents, studentList, schoolList);
			urlcontents="";
		}
		String filecontents = sort(studentList);
		filecontents = filecontents + sort(schoolList);
		String filename=contestField[chosen]+chosenClass+"A.html";
		try
		{	
			System.out.println(filecontents.length());
			System.out.println("Writing "+filename);	
			DataOutputStream Fout = new DataOutputStream(new FileOutputStream(filename));
			int count;
			for( count=0; count<filecontents.length()/65535; count++)
				Fout.writeUTF(filecontents.substring(count*65535, count*65535+65535));		
			Fout.writeUTF(filecontents.substring(count*65535));
			Fout.flush();
			Fout.close();

		}
		catch (Exception e)
		{
			System.out.println("Failed writing "+filename);
			e.printStackTrace();
		}
	}
	}
	}
	
	public void sift(StringTokenizer st, int count)
	{
		for (int i = 0; i < count; i++) {System.out.println(""+i+" "+st.nextToken());}
	}
	
	//skip the appropriate number of steps to find the data
	public void pause(StringTokenizer st)
	{
		String foo="";
		while (st.hasMoreTokens() && !foo.equals("td  class=\"fineprnt\" nowrap=\"nowrap\""))
		{
			if (st.hasMoreTokens())
			foo = st.nextToken();
			else break;
		}
	}
	
	//another skipper
	public void ppause(StringTokenizer st)
	{
		String foo="";
		while (st.hasMoreTokens() && !foo.equals("td  class=\"fineprnt\" nowrap=\"nowrap\""))
		{	if (st.hasMoreTokens())
			foo = st.nextToken();
			else break;
		}
	}
	
	//a third skipper
	public void qpause(StringTokenizer st)
	{
		String foo="";
		while (st.hasMoreTokens() && !foo.equals("td  class=\"fineprnt\" style=\"text-align:right;\" nowrap=\"nowrap\""))
		{
			if (st.hasMoreTokens())
			foo = st.nextToken();
			else break;
		}
	}
	
	public int scoreit(String s)
	{
		int nega = 1;
		if (s.indexOf("-") != -1)
			nega = -1;
		
		while (s.charAt(0)<'0')
			s=s.substring(1);
		
		return (int)(Double.parseDouble(s)*nega);
	}
	
	public void process(String urlContents, ArrayList studentList, ArrayList schoolList)
	{
		StringTokenizer st = new StringTokenizer(urlContents,"<>");
		StringTokenizer st2 = new StringTokenizer(urlContents,"<>");
		String foo="";
		String name="",school="",score="",place="";
		while (st.hasMoreTokens() && !foo.equals("Individual Results"))
		{
			if (st.hasMoreTokens()) foo = st.nextToken(); else break;
		}
		
		while (st.hasMoreTokens() && !foo.equals("Team Results"))
		{
			if (st2.hasMoreTokens())foo = st2.nextToken(); else break;
		}
		while (st.hasMoreTokens())
		{
			pause(st);
			if (st.hasMoreTokens())name=st.nextToken();else break;
			//if (name.indexOf("H S")==-1) break;
			int comma = name.indexOf(",");
			if (comma!=-1)
				name = name.substring(comma+2)+" "+name.substring(0,comma);
			else
				break;
			pause(st);	//jump to school
			if (st.hasMoreTokens())	school = st.nextToken(); else break;
//			System.out.println(school);	//debugging
			qpause(st);	//jump to score
			if (st.hasMoreTokens())score = st.nextToken(); else break;
//			System.out.println(score);	//debugging
			int stuscore = scoreit(score);
			qpause(st);	//jump to place
			if (st.hasMoreTokens())  place = st.nextToken();else break;
//			System.out.println(place);	//debugging
//			qpause(st);	//debugging
//			qpause(st);	//debugging
			if (st.hasMoreTokens())foo = st.nextToken();else break;
			if (school.equals("1") ) continue;
			Student s = new Student(name, school, stuscore,""+j+"-"+chosenClass,place);
			studentList.add(s);
		}
		while (st2.hasMoreTokens() && !foo.equals("Team Results"))
		{
			pause(st2);
			if (st2.hasMoreTokens()) name=st2.nextToken(); else break;
			//schools should be longer than for chars
			if (name.length()<4)
				break;
//			System.out.println(name);	//debugging
			pause(st2);
			if (st2.hasMoreTokens())school = st2.nextToken(); else break;
//			System.out.println(school);	//debugging
			qpause(st2);
			if (st2.hasMoreTokens())score = st2.nextToken(); else break;
//			System.out.println(score);	//debugging
			int stuscore = scoreit(score);
			qpause(st2);
			//if (st2.hasMoreTokens()) pause(st2); else break;
			if (st2.hasMoreTokens()) place = st2.nextToken();else break;
			Student s = new Student ("", name, stuscore,""+j+"-"+chosenClass,place);
			schoolList.add(s);
		}


	}
	
	public String sort (ArrayList list)
	{
		String result = "<TABLE border=\"1\" cellpadding=\"1\"><TR><TD>Overall<BR>rank</TD><TD>Score</TD><TD>Name</TD><TD>School</TD><TD>District</TD><TD>District<BR>Rank</TD></TR><TR>";
		Student s=new Student("","",0,"",""),t,prev;
		int count=0, tempcount=1;
		while (!list.isEmpty())
		{	
			prev=s;
			ListIterator it = list.listIterator();
			s = (Student)(it.next());
			while (it.hasNext())
			{
				t = (Student)(it.next());
				if (t.getScore() > s.getScore())
					s = t;
			}
			list.remove(s);
			if (s.getScore() == prev.getScore())
				tempcount++;
			else
				{count += tempcount; tempcount=1;}
			result+="<TD>"+count+"</TD><TD>"+s.getScore()+"</TD><TD>"+s.getName()+"</TD><TD>"+s.getSchool()+"</TD><TD>"+s.getDist()+"A</TD><TD>"+s.getPlace()+"</TD></TR><TR>";

		}	
		return result;	
	}
	
	public static void main (String a[])
	{
		new Organizer();
		System.exit(0);
	}
}

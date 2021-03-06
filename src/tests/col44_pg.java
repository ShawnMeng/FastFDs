//package algorithm;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ListIterator;

public class col44_pg {
	private static Connection connection;
	private static Integer numberOfAttributes;
	private static String table;
	private static long query_time;
	private static long subquery_time;
	private static Vector<String> attrStr;
	private static Statement st;

	
	public static void init(String[] cmdinput){

//		numberOfAttributes = Integer.parseInt(cmdinput[1]);                 
 		table = cmdinput[0];
		attrStr = new Vector<String>(0);
//		primaryKey = 0;
//		lattice = new Vector<Integer>(0);
//		depen_set = new Hashtable<Integer,HashSet<Integer>>(0);
//		time_ref = new Hashtable<Integer, Integer>(0);
//		check_subset_time = new Integer(0);
//		add_to_exist_time = new Integer(0);
		query_time = new Integer(0);
		subquery_time = new Integer(0);
//		query_times = new Integer(0);

		try {
 
			Class.forName("org.postgresql.Driver");
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;
 
		}

		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/postgres", "postgres",
					"");
//			connection = DriverManager.getConnection(
//					"jdbc:postgresql://127.0.0.1:5432/postgres?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory", "postgres",
//					"");
 			connection.setAutoCommit(false);
 			st = connection.createStatement();

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}

	
	private static void get_diffset(){
		try {
			
//			String get_column_name = "select column_name"
//					+ "from information_schema.columns"
//					+ "where table_name='"
//					+ table
//					+ "';
			
			String add_id = "alter table " + table + " add id SERIAL";
			 
			String inner_join = "CREATE VIEW joined AS "
					+ "SELECT row_number() OVER() AS id, t1.a AS A1, t2.a AS A2, t1.b AS B1, t2.b AS B2, t1.c AS C1, t2.c AS C2, t1.d AS D1, t2.d AS D2, "
					+ "t1.e AS E1, t2.e AS E2, t1.f AS F1, t2.f AS F2, t1.g AS G1, t2.g AS G2, t1.h AS H1, t2.h AS H2, t1.i AS I1, t2.i AS I2, t1.j AS J1, t2.j AS J2, t1.k AS K1, t2.k AS K2, "
					+ "t1.l AS L1, t2.l AS L2, t1.m AS M1, t2.m AS M2, t1.n AS N1, t2.n AS N2, t1.o AS O1, t2.o AS O2, "
					+ "t1.p AS P1, t2.p AS P2, t1.q AS Q1, t2.q AS Q2, t1.r AS R1, t2.r AS R2, t1.s AS S1, t2.s AS S2, t1.t AS T1, t2.t AS T2, t1.u AS U1, t2.u AS U2, t1.v AS V1, t2.v AS V2, "
					+ "t1.w AS w1, t2.w AS w2, t1.x AS x1, t2.x AS x2, t1.y AS y1, t2.y AS y2, t1.z AS z1, t2.z AS z2, t1.aa AS aa1, t1.aa AS aa2, t1.ab as ab1, t2.ab AS ab2, "
					+ "t1.ac AS ac1, t2.ac AS ac2, t1.ad AS ad1, t2.ad AS ad2, t1.ae AS ae1, t2.ae AS ae2, t1.af AS af1, t2.af AS af2, t1.ag AS ag1, t2.ag AS ag2, t1.ah AS ah1, t2.ah AS ah2, t1.ai AS ai1, t2.ai AS ai2, "
					+ "t1.aj AS aj1, t2.aj AS aj2, t1.ak AS ak1, t2.ak AS ak2, t1.al AS al1, t2.al AS al2, t1.am AS am1, t2.am AS am2, "
					+ "t1.an AS an1, t2.an AS an2, t1.ao AS ao1, t2.ao AS ao2, t1.ap AS ap1, t2.ap AS ap2, t1.aq AS aq1, t2.aq AS aq2, t1.ar AS ar1, t2.ar AS ar2 "
				    + "FROM "+ table +" t1 "
                        + "INNER JOIN "+ table +" t2 "
					+ "ON t1.id <t2.id";		
	
			String diff_set = "CREATE VIEW diffset AS "
					+ "SELECT id, 'a' AS Diff "
					+ "FROM ( "
					+ "SELECT id, a1, a2 FROM joined "
					+ "WHERE a1 <> a2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'b' AS Diff "
					+ "FROM ( "
					+ "SELECT id, b1, b2 FROM joined "
					+ "WHERE b1 <> b2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'c' AS Diff "
					+ "FROM ( "
					+ "SELECT id, c1, c2 FROM joined "
					+ "WHERE c1 <> c2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'd' AS Diff "
					+ "FROM ( "
					+ "SELECT id, d1, d2 FROM joined "
					+ "WHERE d1 <> d2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'e' AS Diff "
					+ "FROM ( "
					+ "SELECT id, e1, e2 FROM joined "
					+ "WHERE e1 <> e2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'f' AS Diff "
					+ "FROM ( "
					+ "SELECT id, f1, f2 FROM joined " 
					+ "WHERE f1 <> f2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'g' AS Diff "
					+ "FROM ( "
					+ "SELECT id, g1, g2 FROM joined " 
					+ "WHERE g1 <> g2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'h' AS Diff "
					+ "FROM ( "
					+ "SELECT id, h1, h2 FROM joined " 
					+ "WHERE h1 <> h2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'i' AS Diff "
					+ "FROM ( "
					+ "SELECT id, i1, i2 FROM joined " 
					+ "WHERE i1 <> i2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'j' AS Diff "
					+ "FROM ( "
					+ "SELECT id, j1, j2 FROM joined " 
					+ "WHERE j1 <> j2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'k' AS Diff "
					+ "FROM ( "
					+ "SELECT id, k1, k2 FROM joined " 
					+ "WHERE k1 <> k2 "
					+ ") AS Diffs";

            String  diff_set_alt = "SELECT id, 'a' AS Diff "
                    + "FROM ( "
                    + "SELECT id, a1, a2 FROM joined "
                    + "WHERE a1 <> a2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'b' AS Diff "
                    + "FROM ( "
                    + "SELECT id, b1, b2 FROM joined "
                    + "WHERE b1 <> b2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'c' AS Diff "
                    + "FROM ( "
                    + "SELECT id, c1, c2 FROM joined "
                    + "WHERE c1 <> c2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'd' AS Diff "
                    + "FROM ( "
                    + "SELECT id, d1, d2 FROM joined "
                    + "WHERE d1 <> d2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'e' AS Diff "
                    + "FROM ( "
                    + "SELECT id, e1, e2 FROM joined "
                    + "WHERE e1 <> e2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'f' AS Diff "
                    + "FROM ( "
                    + "SELECT id, f1, f2 FROM joined "
                    + "WHERE f1 <> f2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'g' AS Diff "
                    + "FROM ( "
                    + "SELECT id, g1, g2 FROM joined "
                    + "WHERE g1 <> g2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'h' AS Diff "
                    + "FROM ( "
                    + "SELECT id, h1, h2 FROM joined "
                    + "WHERE h1 <> h2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'i' AS Diff "
                    + "FROM ( "
                    + "SELECT id, i1, i2 FROM joined "
                    + "WHERE i1 <> i2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'j' AS Diff "
                    + "FROM ( "
                    + "SELECT id, j1, j2 FROM joined "
                    + "WHERE j1 <> j2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'k' AS Diff "
                    + "FROM ( "
                    + "SELECT id, k1, k2 FROM joined "
                    + "WHERE k1 <> k2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'l' AS Diff "
					+ "FROM ( "
					+ "SELECT id, l1, l2 FROM joined "
					+ "WHERE l1 <> l2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'm' AS Diff "
					+ "FROM ( "
					+ "SELECT id, m1, m2 FROM joined "
					+ "WHERE m1 <> m2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'n' AS Diff "
					+ "FROM ( "
					+ "SELECT id, n1, n2 FROM joined "
					+ "WHERE n1 <> n2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'o' AS Diff "
					+ "FROM ( "
					+ "SELECT id, o1, o2 FROM joined "
					+ "WHERE o1 <> o2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'p' AS Diff "
					+ "FROM ( "
					+ "SELECT id, p1, p2 FROM joined "
					+ "WHERE p1 <> p2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'q' AS Diff "
					+ "FROM ( "
					+ "SELECT id, q1, q2 FROM joined " 
					+ "WHERE q1 <> q2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'r' AS Diff "
					+ "FROM ( "
					+ "SELECT id, r1, r2 FROM joined " 
					+ "WHERE r1 <> r2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 's' AS Diff "
					+ "FROM ( "
					+ "SELECT id, s1, s2 FROM joined " 
					+ "WHERE s1 <> s2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 't' AS Diff "
					+ "FROM ( "
					+ "SELECT id, t1, t2 FROM joined " 
					+ "WHERE t1 <> t2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'u' AS Diff "
					+ "FROM ( "
					+ "SELECT id, u1, u2 FROM joined " 
					+ "WHERE u1 <> u2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'v' AS Diff "
					+ "FROM ( "
					+ "SELECT id, v1, v2 FROM joined " 
					+ "WHERE v1 <> v2 "
					+ ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'w' AS Diff "
                    + "FROM ( "
                    + "SELECT id, w1, w2 FROM joined "
                    + "WHERE w1 <> w2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'x' AS Diff "
                    + "FROM ( "
                    + "SELECT id, x1, x2 FROM joined "
                    + "WHERE x1 <> x2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'y' AS Diff "
                    + "FROM ( "
                    + "SELECT id, y1, y2 FROM joined "
                    + "WHERE y1 <> y2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'z' AS Diff "
                    + "FROM ( "
                    + "SELECT id, z1, z2 FROM joined "
                    + "WHERE z1 <> z2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'aa' AS Diff "
                    + "FROM ( "
                    + "SELECT id, aa1, aa2 FROM joined "
                    + "WHERE aa1 <> aa2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'ab' AS Diff "
                    + "FROM ( "
                    + "SELECT id, ab1, ab2 FROM joined "
                    + "WHERE ab1 <> ab2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'ac' AS Diff "
                    + "FROM ( "
                    + "SELECT id, ac1, ac2 FROM joined "
                    + "WHERE ac1 <> ac2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'ad' AS Diff "
                    + "FROM ( "
                    + "SELECT id, ad1, ad2 FROM joined "
                    + "WHERE ad1 <> ad2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'ae' AS Diff "
                    + "FROM ( "
                    + "SELECT id, ae1, ae2 FROM joined "
                    + "WHERE ae1 <> ae2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'af' AS Diff "
                    + "FROM ( "
                    + "SELECT id, af1, af2 FROM joined "
                    + "WHERE af1 <> af2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'ag' AS Diff "
                    + "FROM ( "
                    + "SELECT id, ag1, ag2 FROM joined "
                    + "WHERE ag1 <> ag2 "
                    + ") AS Diffs "
                    + "UNION "
                    + "SELECT id, 'ah' AS Diff "
					+ "FROM ( "
					+ "SELECT id, ah1, ah2 FROM joined "
					+ "WHERE ah1 <> ah2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'ai' AS Diff "
					+ "FROM ( "
					+ "SELECT id, ai1, ai2 FROM joined "
					+ "WHERE ai1 <> ai2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'aj' AS Diff "
					+ "FROM ( "
					+ "SELECT id, aj1, aj2 FROM joined "
					+ "WHERE aj1 <> aj2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'ak' AS Diff "
					+ "FROM ( "
					+ "SELECT id, ak1, ak2 FROM joined "
					+ "WHERE ak1 <> ak2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'al' AS Diff "
					+ "FROM ( "
					+ "SELECT id, al1, al2 FROM joined "
					+ "WHERE al1 <> al2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'am' AS Diff "
					+ "FROM ( "
					+ "SELECT id, am1, am2 FROM joined " 
					+ "WHERE am1 <> am2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'an' AS Diff "
					+ "FROM ( "
					+ "SELECT id, an1, an2 FROM joined " 
					+ "WHERE an1 <> an2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'ao' AS Diff "
					+ "FROM ( "
					+ "SELECT id, ao1, ao2 FROM joined " 
					+ "WHERE ao1 <> ao2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'ap' AS Diff "
					+ "FROM ( "
					+ "SELECT id, ap1, ap2 FROM joined " 
					+ "WHERE ap1 <> ap2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'aq' AS Diff "
					+ "FROM ( "
					+ "SELECT id, aq1, aq2 FROM joined " 
					+ "WHERE aq1 <> aq2 "
					+ ") AS Diffs "
					+ "UNION "
					+ "SELECT id, 'ar' AS Diff "
					+ "FROM ( "
					+ "SELECT id, ar1, ar2 FROM joined " 
					+ "WHERE ar1 <> ar2 "
					+ ") AS Diffs";

			
			String diffset_output = "SELECT id, array_agg(diff) AS DifferenceSets "
					+ "FROM diffset "
					+ "GROUP BY id";
			
			String diffset_output_alt = "select t.id as id, array(select diff from diffset as c "
					+ "where c.id = t.id) as diffs "
					+ "from diffset as t "
					+ "group by t.id";
			
//			String receive_diffset = "SELECT * FROM diffset_output";
			
			long start1 = System.currentTimeMillis();
			Statement st1 = connection.createStatement();
			st1.executeUpdate(add_id);
//			System.out.println(add_id);
			System.out.println("ID column added");
			subquery_time = System.currentTimeMillis() - start1;
			query_time = query_time + subquery_time;
			System.out.println("Adding key: " + subquery_time);
			
			
			long start2 = System.currentTimeMillis();
			Statement st2 = connection.createStatement();
			st2.executeUpdate(inner_join);
//			System.out.println(inner_join);
			System.out.println("Joined view created for " + table);
			subquery_time = System.currentTimeMillis() - start2;
			query_time = query_time + subquery_time;
			System.out.println("Creating self-joined view: " + subquery_time);

                       
            long start3 = System.currentTimeMillis();
            Statement st3 = connection.createStatement();
//                        st3.executeUpdate(diff_set);
            connection.setAutoCommit(false);
            st3.setFetchSize(50);
            ResultSet rs = st3.executeQuery(diff_set_alt);
            System.out.println("Differences spotted");
            subquery_time = System.currentTimeMillis() - start3;
            query_time = query_time + subquery_time;
            System.out.println("Generating diffs: " + subquery_time);
		
	
//			long start4 = System.currentTimeMillis();
//			Statement st4 = connection.createStatement();
//			ResultSet rs = st4.executeQuery(diffset_output_alt);
//			System.out.println(diffset_output);
//			System.out.println("Diffset view created and received!");
//			subquery_time = System.currentTimeMillis() - start4;
//			query_time = query_time + subquery_time;
//			System.out.println("Creating and aggregating diffsets: " + subquery_time);

			
//			long start5 = System.currentTimeMillis();
//			Statement st5 = connection.createStatement();
//			ResultSet rs = st5.executeQuery(receive_diffset);
//			System.out.println("Diffset received!");
//			query_time = query_time + (System.currentTimeMillis() - start5);
//			System.out.println("Receiving diffsets: " + query_time);

			
//			while(rs.next()){
			
//				Integer first = rs.getInt(1);
//			    String second = rs.getString(2);
//				System.out.println(first + " " + second);
				
//			}
		
            rs.close();
            st1.close();
            st2.close();   
            st3.close();
//          st4.close();


		} catch(SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}
	
	
	public static void main(String[] argv) {
  		long total = System.currentTimeMillis();

		init(argv);

		get_diffset();
		
		System.out.println("total time: " + (System.currentTimeMillis() - total));
		System.out.println("query_time: " + query_time);
	}
	
}

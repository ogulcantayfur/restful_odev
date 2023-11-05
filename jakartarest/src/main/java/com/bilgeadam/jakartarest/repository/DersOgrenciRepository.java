package com.bilgeadam.jakartarest.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.bilgeadam.jakartarest.Constants;
import com.bilgeadam.jakartarest.model.DersOgrenci;

public class DersOgrenciRepository
{

	public boolean save(DersOgrenci dogrn) throws SQLException
	{
		boolean result = false;
		Connection con = Constants.getConnection();
		String sql = "INSERT INTO \"public\".\"DERS_OGRENCI\"(\"DERS_ID\", \"OGR_ID\",\"DEVAMSIZLIK\",\"NOTE\") VALUES (?, ?, ?, ?)";
		PreparedStatement stmnt = con.prepareStatement(sql);	
		stmnt.setLong(1, dogrn.getDERS_ID());
		stmnt.setLong(2, dogrn.getOGR_ID());
		stmnt.setInt(3, dogrn.getDEVAMSIZLIK());
		stmnt.setInt(4, dogrn.getNOTE());
		result = stmnt.executeUpdate() == 1;
		stmnt.close();
		con.close();
		return result;
	}

	public boolean deleteByID(long id) throws SQLException
	{
		Connection con = Constants.getConnection();
		String sql = "delete from \"public\".\"DERS_OGRENCI\" where \"ID\" = ?";
		PreparedStatement stmnt = con.prepareStatement(sql);
		stmnt.setLong(1, id);
		boolean result = stmnt.executeUpdate() == 1;
		stmnt.close();
		con.close();
		return result;
	}

	public ArrayList<DersOgrenci> getAll() throws SQLException
	{
		ArrayList<DersOgrenci> list = new ArrayList<>();
		Connection con = Constants.getConnection();
		Statement stmnt = con.createStatement();
		ResultSet result = stmnt.executeQuery("select * from \"public\".\"DERS_OGRENCI\" order by \"ID\" asc");
		while (result.next())
		{
			long id = result.getLong("ID");
			long ders_id = result.getLong("DERS_ID");
			long ogr_id = result.getLong("OGR_ID");
			int devamsızlık = result.getInt("DEVAMSIZLIK");
			int not = result.getInt("NOTE");
			System.out.println("burası"+id+ders_id+ogr_id+devamsızlık+not);
			list.add(new DersOgrenci(id, ders_id, ogr_id, devamsızlık, not));
		}
		result.close();
		stmnt.close();
		con.close();
		return list;
	}

	public DersOgrenci getByID(long id) throws SQLException
	{
		DersOgrenci dogrn = null;
		Connection con = Constants.getConnection();
		String sql = "select * from \"public\".\"DERS_OGRENCI\" where \"ID\" = ?";
		PreparedStatement stmnt = con.prepareStatement(sql);
		stmnt.setLong(1, id);
		ResultSet result = stmnt.executeQuery();
		while (result.next())
		{
			dogrn = new DersOgrenci(result.getLong("ID"), result.getLong("DERS_ID"), result.getLong("OGR_ID"), result.getInt("DEVAMSIZLIK"), result.getInt("NOTE"));
		}
		result.close();
		stmnt.close();
		con.close();
		return dogrn;
	}
}

package com.hackathon.Events.services;

import org.springframework.stereotype.Service;


@Service
public class MainService {
	
	/*@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	
	public List<Club> getAllClubs(){
		String sql = "SELECT * FROM CLUB";
		return jdbcTemplate.query(sql, 
				new RowMapper<Club>() {

					@Override
					public Club mapRow(ResultSet rs, int rowNum) throws SQLException {
						Club c = new Club();
						c.setName(rs.getString("CLUB_NAME"));
						c.setId(rs.getString("ID"));
						c.setLogo(rs.getString("LOGO"));
						return c;
					}
			
		});
	}
	
	
	public List<Event> search(String clubId, String name){
		List<String> l = new ArrayList<String>();
		String sql = "SELECT * FROM (SELECT ROW_.*, ROWNUM ROWNUM_ FROM (SELECT * FROM CLUB_EVENTS ";
		if(StringUtils.hasText(name)) {
			sql+="WHERE NAME = ?";
			l.add(name);
			if(StringUtils.hasText(clubId)) {
				sql+=" AND CLUB_ID = ?";
				l.add(clubId);
			}
		}
		else if(StringUtils.hasText(clubId)){
			sql+="WHERE CLUB_ID = ?";
			l.add(clubId);
		}
		return jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                for(int i=0; i<l.size(); i++) {
                	preparedStatement.setString(i+1, l.get(i));
                }
            }
        }, new RowMapper<Event>() {

			@Override
			public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Event e = new Event();
				e.setClubId(rs.getString("CLUB_ID"));
				e.setClub(findClubById(e.getClubId()));
				e.setDescription(rs.getString("EVENT_DESCRIPTION"));
				e.setLogo(rs.getString("LOGO"));
				e.setName(rs.getString("EVENT_NAME"));
				e.setId(rs.getString("ID"));
				return e;
			}
        	
        });
		
	}
	
	
	public Club findClubById(String id) {
		return jdbcTemplate.query("SELECT * FROM  CLUB WHERE ID = ?", new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setString(1, id);
			}
			
		}, new RowMapper<Club>() {

			@Override
			public Club mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Club c = new Club();
				c.setName(rs.getString("CLUB_NAME"));
				c.setId(rs.getString("ID"));
				c.setLogo(rs.getString("LOGO"));
				return c;
			}
			
		}).get(0);
	}*/
	
}

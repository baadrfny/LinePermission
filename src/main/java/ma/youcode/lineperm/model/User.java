package ma.youcode.lineperm.model;



public class User{


	private String username;
	private String passHach;


	public User(String username , String passHach){

		this.username = username;
		this.passHach = passHach;

	}

	public String getUsername(){

		return username;

	}

	public String getPass(){

		return passHach;

	}


}

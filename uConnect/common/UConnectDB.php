<?php
include_once("MyDatabase.php");
	class UConnectDB extends MyDatabase
	{
		private $link = null;
		
		public function __construct($login, $password, $database, $hostname)
		{
			parent::__construct($login, $password, $database, $hostname);
//			$this->connect();
//			$this->selectDatabase();
		}
		/*
		*	Connect to the database
		*
		*	@return false on failure/ true on success.
		*
		*/
		public function connect()
		{
			if(! is_null($this->link))
			{
				return false;
			}
			$link = @mysql_connect($this->hostname, $this->login, $this->password);
			if(! $link)
			{
                		return false;
            		}
            		return true;
		}
		/*
		*	Selects the database from connected db.
		*	
		*	@returns false on failure / true on success.
		*
		*/
		public function selectDatabase()
		{
			$ret = @mysql_select_db($this->database);
			if(! $ret)
			{
                		return false;
            		}
            		return true;
        	}
		/*
		*	Disconnects the selected databse.
		*
		*	@returns false on failure/ true on success.
		*
		*/
        	public function disconnect()
        	{
            		if($this->link)
            		{
                		if(@mysql_close())
                		{
                    			$this->link = false;
                    			return true;
                		}
                		else
                		{
                    			return false;
                		}
            		}
			return false;
        	}
		/*
		*	Query the database.
		*	
		*	@param $query - query string to database.
		*	@return the results of the mysql query function.
		*/
		public function query($query)
		{
			if($this->link)
			{
				$result = $this->link->query($query);
				return $result;
			}
		}
		/*
		*	Fetch rows from database (SELECT query)
		*
		*	@param $query - select query string to database.
		*	@return bool False on failure/ array database rows on success.
		*
		*/
		public function select($query)
		{
			$rows = array();
			$result = $this->query($query);
			if($result == false)
			{
				return false;
			}
			while($row = $result->fetch_assoc())
			{
				$rows[] = $row;
			}
			return $rows;
		}
	}
?>

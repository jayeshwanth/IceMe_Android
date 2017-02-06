<?php
	abstract class MyDatabase
	{
		protected $login;
		protected $password;
		protected $database;
		protected $hostname;

		public function __construct($login, $password, $database, $hostname)
		{
			$this->throwExceptionIfNotSet('login', $login);
			$this->throwExceptionIfNotSet('password', $password);
			$this->throwExceptionIfNotSet('database', $database);
			$this->throwExceptionIfNotSet('hostname', $hostname);

			$this->login = $login;
			$this->password = $password;
			$this->database = $database;
			$this->hostname = $hostname;
		}
		private function throwExceptionIfNotSet($argName, $argValue)
		{
			if(empty($argValue))
			{
				throw new MyDBException("'${argName}' not set");
			}
		}
	}
?>

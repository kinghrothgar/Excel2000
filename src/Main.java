public class Main
{

	/**
	 * Starts the SQL Query Command-line interface.
	 * User can choose options using numeric input.
	 * Data can be manipulated through alphanumeric input.
	 */
	public static void main(String[] args)
	{
		CommandLineInterface cli = new CommandLineInterface(System.out, System.in);
		cli.start();
	}
}

public class ReaderTagItem extends Reader
{
    private static String query;

    public ReaderTagItem(String file)
    {
        super(file);
        this.query = "insert into tag (id, tag_name) values (?, ?)";
    }

    public String getQuery()
    {
        return this.query;
    }

    public String[][] getItemsToInsert()
    {
        String[][] insertItems = new String[this.lines.size()][2];
        for(int i = 0; i < this.lines.size(); i++)
        {
            String[] tokens = this.lines.get(i).split("\t");
            insertItems[i][0] = tokens[0];
            insertItems[i][1] = tokens[1];
        }
        return insertItems;
    }
}

package util.album;

final public class AlbumPropertiesUtils
{
    public static String getAlbumIdFromAlbumProperties(String albumProperties)
    {
        String[] albumPropertiesArray = albumProperties.split(":");
        return albumPropertiesArray[albumPropertiesArray.length-1].trim();
    }
}

<xsl:stylesheet
 version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

 <xsl:output indent="yes" cdata-section-elements="style" />
 <xsl:template match="/">
 <gpx>
 <xsl:for-each select="/collection/posicao">
 <xsl:variable name="lat" select="lat"/>
 <xsl:variable name="lon" select="lon"/>

 <wpt lat="{$lat}" lon="{$lon}">
 <ele>0.0</ele>
 <time><xsl:value-of select="timestamp" /></time>
 <name><xsl:value-of select="login" /></name>
 <type>Point</type>
 </wpt>
 </xsl:for-each>
 </gpx>
 </xsl:template>
</xsl:stylesheet>
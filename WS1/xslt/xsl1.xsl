<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
        <!--<xsl:template match="client/*">
            <xsl:copy>
                <xsl:apply-templates select="@*|node()" />
            </xsl:copy>
        </xsl:template>-->
        <xsl:template match="wallet">
        </xsl:template>		
		<xsl:template match="person/*">
			<xsl:element name="person">
				<xsl:element name="job">programmer</xsl:element>
				<xsl:copy>
					<xsl:apply-templates select="node()" />
				</xsl:copy>
			</xsl:element>
        </xsl:template>  
</xsl:stylesheet>
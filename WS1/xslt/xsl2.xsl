<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
        <!--<xsl:template match="client">
            <xsl:copy>
                <xsl:apply-templates select="@*|node()" />
            </xsl:copy>
        </xsl:template>-->
        <xsl:template match="person">
        </xsl:template>
        <xsl:template match="wallet/*">
			<xsl:element name="wallet">
				<xsl:element name="accountType">banc account</xsl:element>
				<xsl:copy>
					<xsl:apply-templates select="node()" />
				</xsl:copy>
			</xsl:element>
        </xsl:template>    
</xsl:stylesheet>
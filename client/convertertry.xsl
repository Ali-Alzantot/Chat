<?xml version="1.0"?>

<!--
    Document   : convertertry.xsl
    Created on : January 19, 2018, 10:49 AM
    Author     : Dell Laptop
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/Messages">
        <html>
            <head>
                <title>convertertry.xsl</title>
            </head>
            <body>
                <div style="width: 421px; height: 435px; background:#e7f6f5;">
			<div style="height: 49px; background: #5b5b5b;">
				<table style="height: 49px; width: 421px; background: #5b5b5b;">
					<tr>
						<td rowspan="2" style=" width: 49px; height: 49px"><img src="{URL}" style=" width: 49px; height: 49px"/>
						</td>
						<td style="color: White;">
							<p>name</p>
		            		<p>status</p>
						</td>
					</tr>
				</table>     
	   		</div>
    		<div style="height: 386px; overflow-y: scroll; padding-top: 10">
                    <xsl:for-each select="msg">
     			<div>                        
	            	<table style="width: 400px">
	            		<tr>
	            			<td style="max-width: 360px; float: {direction}; font-size:{size}px; color:{fontcolor}; border-radius:15px; font-family:{family};background-color: {background};">
	           					<div style="float: {direction}; direction: {tDirect};"> <img  src ="user.png" style="width: 28px; height: 28px; margin-top: -5;"/> <span style="display:inline-block; max-width:360; word-wrap: break-word;"><xsl:value-of select="text"/></span></div>
	           				</td>
	            		</tr>
	            	</table>
        		</div>
                        </xsl:for-each>
    		</div>
		</div>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>

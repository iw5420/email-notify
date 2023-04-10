# email-notify
deal with email service, using VelocityEngine to map html with model, using @RestControllerAdvice to deal with global exception handle

how to use VelocityEngine?
we can refer to : http://www.java2s.com/Code/Java/Velocity/UseVelocitytogenerateHTMLbasedemail.htm

I use the code as following.

```java
  public String generateTemplate(Object contentValue, String templatePath) {
    VelocityContext context = new VelocityContext();
    VelocityEngine ve = generateVelocityEngine();
    context.put("contentValue", contentValue);
    Template t = ve.getTemplate(templatePath);
    StringWriter writer = new StringWriter();
    t.merge( context, writer );
    return writer.toString();
``` 
with the template like $item in $contentValue.items, $item.value
```velocity 
#foreach( $item in $contentValue.items )
<tr style="mso-yfti-irow:0;mso-yfti-firstrow:yes">
<td width="30%" valign="top" style="width:30.0%;border:none;
border-top:solid #EAEAEA 1.0pt;mso-border-top-alt:solid #EAEAEA .75pt;
padding:4.5pt 7.5pt 4.5pt 7.5pt">
<p class="MsoNormal" style="line-height:15.0pt"><b><span style="font-size:10.5pt;font-family:&quot;微軟正黑體&quot;,sans-serif;
color:#16B15F">$item.attribute<span lang="EN-US"><o:p></o:p></span></span></b></p>
</td>
<td width="65%" valign="top" style="width:65.0%;border:none;
border-top:solid #EAEAEA 1.0pt;mso-border-top-alt:solid #EAEAEA .75pt;
padding:4.5pt 11.25pt 4.5pt 11.25pt">
<p class="MsoNormal" style="line-height:15.0pt"><span lang="EN-US" style="font-size:10.5pt;font-family:&quot;微軟正黑體&quot;,sans-serif;
color:#494949">$item.value</span></p>
</td>
</tr>
#end
 ``` 

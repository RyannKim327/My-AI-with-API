package mpop.revii.ai;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;
import java.util.ArrayList;

public class Markdown extends TextView {
	String _text = "", noCode = "";
	ArrayList<String> codes = new ArrayList<>();
	ArrayList<String> lang = new ArrayList<>();
	Context ctx;
	public Markdown(Context ctx){
		super(ctx);
		setup(ctx);
	}

	public Markdown(Context ctx, AttributeSet attr){
		super(ctx, attr);
		setup(ctx);
	}

	void setup(Context context){
		ctx = context;
		if(codes.size() <= 0) {
			setClickable(true);
			setTextIsSelectable(true);
			setLinksClickable(true);
		}
	}

	public void setText(String txt){
		String[] text = txt.split("\n");
		String result = "";
		boolean x = true;
		String _code = "";
		for(int i = 0; i < text.length; i++){
			txt = text[i].replaceAll("\\<", "&lt;").replaceAll("\\>", "&gt;");
			boolean keyLanguage = false;
			if(txt.startsWith("``")) {
				keyLanguage = txt.substring("``".length()).length() > 1;
			}
			if(txt.startsWith("```")){
				if(!txt.equals("```")){
					String[] languages = {
						"html", "css"
					};
					if(keyLanguage) {
						char[] c = txt.substring("```".length()).toCharArray();
						String code = String.valueOf(c[0]).toUpperCase();
						for (int i2 = 1; i2 < c.length; i2++) {
							code += String.valueOf(c[i2]);
						}
						for (int ii = 0; ii < languages.length; ii++) {
							if (languages[ii].equalsIgnoreCase(code)) {
								code = code.toUpperCase();
								break;
							}
						}
						lang.add(code);
						result += String.format("<h3><u><i>%s code</i></u></h3>", code);
					}else{
						lang.add("Unknown");
					}
				}else{
					if(x) {
						lang.add("Unknown");
					}
				}
				x = !x;
				result += x ? "</font>" : String.format("<font color=\"%s\">", "#bebebe");
				if(x){
					codes.add(_code);
					_code = "";
				}
			}
			if(x){
				if(txt.startsWith("&gt; ") || txt.startsWith("> ")){
					txt = txt.replaceAll("&gt; (.*)", "&emsp;\"$1\"");
					txt = txt.replace("> (.*)", "&emsp;\"$1\"");
				}
				result += start(txt);
				noCode += start(txt);
			}else{
				if(!txt.startsWith("```")){
					result += txt.replaceAll("\t", "&emsp;").replaceAll("    ", "&emsp;").replaceAll("  ", "&emsp;");
					_code += txt.replaceAll("&lt;", "<").replaceAll( "&gt;", ">");
				}
			}
			if(i < text.length - 1){
				if(!txt.startsWith("* ")){
					result += "<br>";
					_code += (x) ? "" : "\n";
					noCode += "\n";
				}
			}
		}
		setText(Html.fromHtml(result, Html.FROM_HTML_MODE_COMPACT));
	}

	public String getWithoutCode(){
		return Html.fromHtml(noCode).toString();
	}

	private String start(String markdown) {
		String html = markdown;

		html = html.replaceAll("###### (.*)", "<h6 style=\"margin: 0; padding: 0;\">$1</h6>");
		html = html.replaceAll("##### (.*)", "<h5 style=\"margin: 0; padding: 0;\">$1</h5>");
		html = html.replaceAll("#### (.*)", "<h4 style=\"margin: 0; padding: 0;\">$1</h4>");
		html = html.replaceAll("### (.*)", "<h3 style=\" margin: 0; padding: 0;\">$1</h3>");
		html = html.replaceAll("## (.*)", "<h2 style=\"margin: 0; padding: 0;\">$1</h2>");
		html = html.replaceAll("# (.*)", "<h1 style=\"margin: 0; padding: 0;\">$1</h1>");
		
		html = html.replaceAll("\\*\\*(.*?)\\*\\*", "<strong style=\"margin: 0; padding: 0;\">$1</strong>");
		html = html.replaceAll("__(.*?)__", "<strong style=\"margin: 0; padding: 0;\">$1</strong>");

		html = html.replaceAll("\\*(.*?)\\*", "<em style=\"margin: 0; padding: 0;\">$1</em>");
		html = html.replaceAll("_(.*?)_", "<em style=\"margin: 0; padding: 0;\">$1</em>");

		html = html.replaceAll("\\~\\~(.*?)\\~\\~", "<s style=\"margin: 0; padding: 0;\">$1</s>");

		html = html.replaceAll("\\[(.*?)\\]\\((.*?)\\)", "<a href=\"$2\" style=\"margin: 0; padding: 0;\">$1</a>");
		
		html = html.replaceAll("`(.*?)`", "<font style=\"margin: 0; padding: 0;\" color=\"yellow\">$1</font>");
		html = html.replaceAll("\t", "&emsp;");
		html = html.replaceAll("    ", "&emsp;");
		html = html.replaceAll("  ", "&emsp;");

		html = html.replaceAll("`", "");

		html = html.replaceAll("\\-\\-\\-", "<hr>");
		
		
		html = html.replaceAll("\\* (.*)", "&ensp;•&ensp;$1<br>");
		
		return html;
	}
	public ArrayList<String> getAllCodes(){
		return codes;
	}
	public ArrayList<String> getLanguage(){
		return lang;
	}
}

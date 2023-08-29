package modules.customers.Interaction;

import org.skyve.util.Util;

import com.ibm.icu.text.SimpleDateFormat;

import modules.customers.domain.Interaction;

public class InteractionExtension extends Interaction {

	private static final long serialVersionUID = 7036377442570071671L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a, dd/MM/yy");

	@Override
	public String getInteractionTemplate() {
		String type = getType().toString();
		switch (type) {
		case "email":
			type = "fa fa-envelope";
			break;
		case "phone":
			type = "fa fa-phone";
			break;
		case "meeting":
			type = "fa fa-users";
			break;
		case "socialMedia":
			type = "fa fa-share-alt-square";
			break;
		case "comment":
			type = "fa fa-comment";
			break;
		case "upload":
			type = "fa fa-file";
			break;
		case "other":
			type = "fa fa-info-circle";
			break;
		default:
			break;
		}

		StringBuilder markup = new StringBuilder();
		markup.append("<style>" + ".container {\r\n" + "	overflow: hidden;\r\n"
				+ "	border-left: 4px solid #007ad9;	\r\n" + "}\r\n" + ".container:hover {\r\n"
				+ "	border-left: 4px solid #007ad9;\r\n" + "	background-color: rgba(0, 122, 217, 0.2);\r\n"
				+ "	cursor: pointer;\r\n" + "}\r\n" + "\r\n" + ".infoTemp {\r\n" + "  	float: left;\r\n"
				+ "  	text-align: left;\r\n" + "  	width: 60%;\r\n" + "  	margin-left: 10px;\r\n"
				+ "  	line-height: 30px;\r\n" + "}\r\n" + "\r\n" + ".dateAuth {\r\n" + "  	float: left;\r\n"
				+ "  	line-height: 30px;\r\n" + "  	text-align: end;\r\n" + "}\r\n" + ".iconTemp {\r\n"
				+ " 	float: left;\r\n" + "  	margin-top: 20px;\r\n" + "  	text-align: center;\r\n"
				+ "  	width: 10%;\r\n" + "  	font-size: 25px;\r\n" + "}\r\n" + "\r\n" + ".titleTemp {\r\n"
				+ "  	font-weight: bold;\r\n" + "}\r\n" + "\r\n" + ".descriptionTemp {\r\n"
				+ "  	font-style: italic;\r\n" + "  	color: grey;\r\n" + "}\r\n" + "\r\n" + ".dateTemp {\r\n"
				+ "  	color: grey;\r\n" + "}\r\n" + "\r\n" + ".authorTemp {\r\n" + "  	color: grey;\r\n" + "}"
				+ "</style>");
		markup.append("<div class='container' onclick=\"location.href='"+ Util.getDocumentUrl(this) +"';\">"
				+ "<div class='iconTemp'><span class='"+ type +"'></span></div>");
		markup.append("<div class='infoTemp'><span class='titleTemp'> ").append(getTitle());
		markup.append("</span></br><p class='descriptionTemp'> ");
		if(getDescription()!=null) {
			markup.append(getDescription());
		}
		markup.append("</p></div><div class='dateAuth'><span class='dateTemp'>").append(sdf.format(getInteractionTime()));
		markup.append("</span></br><span class='authorTemp'> ").append(getUser().getContact().getName());
		markup.append("</span></div></div>");
			
		
		return markup.toString();		
	}
	
}

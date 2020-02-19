package bj.modules;

public class bj_messageBox_classes {
	public class BJSimpleListViewInfo {
		public BJSimpleListViewInfo(String ItemTitle, String ItemSubTitle, Boolean Sellected){
			Title=ItemTitle;
			SubTitle=ItemSubTitle;
			this.Sellected=Sellected;

		}
		public String Title="";
		public String SubTitle="";
		public Boolean Sellected=false;

		public BJSimpleListViewInfo() {

		}
	}
}

package adapter.Impl;

import adapter.JsonService;
import adapter.XmlSystem;

/**
 * @Dự án: Bai_05_22669281_NguyenHuuSang_JsonAndXML_Adapter
 * @Class: XmlToJsonAdapter
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class XmlToJsonAdapter implements JsonService {
    private XmlSystem xmlSystem;

    public XmlToJsonAdapter(XmlSystem xmlSystem) {
        this.xmlSystem = xmlSystem;
    }



    @Override
    public void sendData(String jsonData) {
        System.out.println("[Adapter]: Đang chuyển đổi JSON sang XML...");
        //Mô phỏng logic chuyển đổi
        String xmlData = converJsonToXml(jsonData);
        xmlSystem.receiveXml(xmlData);
    }

    private String converJsonToXml(String jsonData) {
        return jsonData.replace("{", "<").replace("}", ">")
                .replace(":", ">").replace("\"", "");
    }
}
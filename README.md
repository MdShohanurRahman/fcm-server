# Firebase Cloud Messaging (FCM) Server for Notifications

🚀 This Spring Boot application serves as a backend server for sending push notifications using Firebase Cloud Messaging.

## Prerequisites

Before getting started, ensure that you have the following prerequisites installed:

- Java Development Kit (JDK)
- Maven
- Firebase Project with FCM configuration

## Getting Started

1. Clone this repository:

   ```bash
   git clone https://github.com/MdShohanurRahman/fcm-server.git
   cd fcm-server
   
2. Configure your Firebase project:
   * Obtain the google-services.json file from your Firebase project and place it in the src/main/resources directory.

3. Build and run the application::

   ```bash
   mvn clean install
   java -jar target/your-fcm-server.jar

The server will start on http://localhost:8080.

## **Endpoints**

### **1\. Send Notification to Device**

Endpoint: `POST /api/v1/notifications/send-to-device`

Example Request:

```json
{
  "title": "Notification Title",
  "body": "Notification Message",
  "imageUrl": "https://cloudinary-marketing-res.cloudinary.com/images/w_1000,c_scale/v1679921049/Image_URL_header/Image_URL_header-png?_i=AA",
  "deviceToken": "dQcnK8K1BfwhNTyH81scC7:APA91bHC-O62Fz6kGQ1QnWkWdI7fs3O-Q7bLVs6HiX86IaphK2gO2aQwP62EFa64RbfAFBtbt5Y3AKLvW3IQq2cmQCzC6yzo-n3vH_E8ovCnIz2ykkHxqTDJx3SkOnS8MoorpVvPKwAE",
  "data": {
      "key1": "value1",
      "key2": "value2"
  }
}
```

### **2\. Send Notification to Topic**

Endpoint: `POST /api/v1/notifications/send-to-topic`

Example Request:

```json
{
    "title": "Ind vs Nz world cup 2023",
    "body": "Ind won.",
    "topicName": "worldCup",
    "imageUrl": "https://cloudinary-marketing-res.cloudinary.com/images/w_1000,c_scale/v1679921049/Image_URL_header/Image_URL_header-png?_i=AA",
    "data": {
        "key1": "value1",
        "key2": "value2"
    }
}
```

### **3\. Send Notification to All Devices**

Endpoint: `POST /api/v1/notifications/send-to-all`

Example Request:

```json
{
    "title": "Notification Title",
    "body": "Notification Message",
    "imageUrl": "https://cloudinary-marketing-res.cloudinary.com/images/w_1000,c_scale/v1679921049/Image_URL_header/Image_URL_header-png?_i=AA",
    "deviceTokenList": [], 
    "data": {
        "key1": "value1",
        "key2": "value2"
    }
}
```

### **4\. Subscribe to Topic**

Endpoint: `POST /api/v1/notifications/subscribe`

Example Request:

```json
{
  "deviceToken": "dQcnK8K1BfwhNTyH81scC7:APA91bHC-O62Fz6kGQ1QnWkWdI7fs3O-Q7bLVs6HiX86IaphK2gO2aQwP62EFa64RbfAFBtbt5Y3AKLvW3IQq2cmQCzC6yzo-n3vH_E8ovCnIz2ykkHxqTDJx3SkOnS8MoorpVvPKwAE",
  "topicName": "worldCup"
}
```

### **5\. Unsubscribe from Topic**

Endpoint: `POST /api/v1/notifications/unsubscribe`

Example Request:

```json
{
  "deviceToken": "dQcnK8K1BfwhNTyH81scC7:APA91bHC-O62Fz6kGQ1QnWkWdI7fs3O-Q7bLVs6HiX86IaphK2gO2aQwP62EFa64RbfAFBtbt5Y3AKLvW3IQq2cmQCzC6yzo-n3vH_E8ovCnIz2ykkHxqTDJx3SkOnS8MoorpVvPKwAE",
  "topicName": "worldCup"
}
```

## **Error Handling**

* If a request fails, the server will respond with an appropriate HTTP status code and an error message in the response body.
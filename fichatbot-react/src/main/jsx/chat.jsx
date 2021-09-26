import { useEffect, useState } from 'react';
import Chat from 'react-simple-chat';
import 'react-simple-chat/src/components/index.css';

const Messenger = () => {
    const [messages, setMessages] = useState([]);

    const getAnswer = (message) => {
        setMessages([...messages, message]); //질문
        const url = `http://localhost:8080/fichatbot/chat/message`;
        fetch(url, {method:"POST", body: JSON.stringify({data:message}), headers:{"Access-Control-Allow-Origin":"*", "Content-Type":"application/json"} })
            .then((res) => res.json())
            .then((data) => {
                setMessages(messages => [...messages, data]); //답변
            }).catch(() => {
                console.log("에러발생");
            });
    };

    const openChat = () => {
        const url = `http://localhost:8080/fichatbot/chat/open`;
        fetch(url, {method:"POST", headers:{"Access-Control-Allow-Origin":"*", "Content-Type":"application/json"}})
            .then((res) => res.json())
            .then((data) => {
                setMessages(messages => [...messages, data]);
            }).catch(()=>{
                console.log("에러발생");
            })
    };

    useEffect(openChat,[]);

    return (
        <Chat
            title="챗봇 샘플"
            user={{ id: 'chatbot' }}
            messages={messages}
            onSend={question=>getAnswer(question) }
        />
    );
};

export default Messenger;
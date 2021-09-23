import { useState } from 'react';
import Chat from 'react-simple-chat';
import 'react-simple-chat/src/components/index.css';

const Messenger = () => {
    const [messages, setMessages] = useState([
        {
            id: 'chatbot',
            text: '안녕하세요 궁금한 것을 물어보세요.',
            createdAt: new Date(),
            user: { id: 'user' }
        }
    ]);

    const getAnswer = (message) => {
        setMessages([...messages, message]); //질문
        const url = `http://localhost:8080/fichatbot/chat`;
        fetch(url, {method:"POST", headers:{"Access-Control-Allow-Origin":"*", "Content-Type":"application/json"} })
            .then((res) => res.json())
            .then((data) => {
                setMessages(messages => [...messages, data]); //답변
            }).catch(() => {
                console.log("에러발생");
            });
    };

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
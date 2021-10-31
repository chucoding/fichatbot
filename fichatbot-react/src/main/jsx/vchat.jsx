import { useEffect, useState } from 'react';
import 'react-simple-chat/src/components/index.css';

// RCE CSS
import 'react-chat-elements/dist/main.css';

import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import IconButton from '@mui/material/IconButton';
import SendIcon from '@mui/icons-material/Send';

import { MessageList } from 'react-chat-elements'

import Vocal from '@untemps/react-vocal'

const VChat = ({ location }) => {

    //변수 선언
    const [messages, setMessages] = useState([]); // 메시지 목록
    const [uuid, setUuid] = useState("");         // ETRI 오픈챗 uuid
    const [storeNum, setStoreNum] = useState(location.props && location.props.num);
    const [question, setQuestion] = useState("");  // STT(Speech To Text)
    const [speech, setSpeech] = useState("");       // 질문

    //함수 선언
	const _onVocalStart = () => {
		setQuestion('')
	}

	const _onVocalResult = (result) => {
        setQuestion(result);
        getAnswer(result);
	}

    const tts = (url) => {
        var audio = document.createElement("Audio");
        audio.src = "http://localhost:8080/fichatbot/resources/tts/"+url; 
        //window.URL.createObjectURL(stream);
        audio.play();
    }

    const getAnswer = (result) => {
        const answer = {
            position: 'right',
            type:'text',
            text:result ? result : question,
            date:new Date()
        };
        
        setMessages([...messages, answer]); //질문
        const url = `http://localhost:8080/fichatbot/chat/message`;
        fetch(url, {method:"POST", body: JSON.stringify({question:answer, uuid:uuid}), headers:{"Access-Control-Allow-Origin":"*", "Content-Type":"application/json"} })
            .then((res) => res.json())
            .then((data) => {
                setQuestion('');
                setMessages(messages => [...messages, data]); //답변
                tts(data.ttsUrl);
            }).catch(() => {
                console.log("에러발생");
            });
    };

    const openChat = () => {
        const url = `http://localhost:8080/fichatbot/chat/open`;
        fetch(url, {method:"POST", body: JSON.stringify({storeNum:storeNum}), headers:{"Access-Control-Allow-Origin":"*", "Content-Type":"application/json"}})
            .then((res) => res.json())
            .then((data) => {
                console.log(data.uuid);
                setUuid(data.uuid);
                setMessages(messages => [...messages, data]);
            }).catch(()=>{
                alert("에러발생 : 서버 켜있는지 확인!!");
            })
    };

    useEffect(openChat,[]);
    useEffect(tts, [speech]);

    //3. html 코드 렌더링
    return (
        <div>
            <Card sx={{height:'96vh', marginTop:'1vh'}}>
                <CardContent style={{backgroundColor:'lightgreen', height:'82vh'}}>
                    <MessageList
                        className='message-list'
                        lockable={true}
                        toBottomHeight={'100%'}
                        dataSource={messages}
                    />
                </CardContent>
                <CardContent className="rce-container-input">
                    <Vocal
                        onStart={_onVocalStart}
                        onResult={_onVocalResult}
                        lang="ko-KR"
                        style={{ display:'flex', flexDirection:'row', margin:'5px' }}
                    />
                    <input class="rce-input" placeholder="메시지를 입력하시오"  onChange={(e)=>setQuestion(e.target.value)} onKeyDown={(e)=> {if(e.key === 'Enter') { getAnswer() }}} value={question} />
                    <div class="rce-input-buttons" onClick={()=>{getAnswer()}}>
                        <IconButton aria-label="전송" ><SendIcon/></IconButton>
                    </div>
                </CardContent>
            </Card>
        </div>
    );
};

export default VChat;
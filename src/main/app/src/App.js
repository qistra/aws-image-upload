import React, {useState, useEffect, useCallback} from 'react';
import logo from './logo.svg';
import './App.css';
import axios from "axios";
import {useDropzone} from 'react-dropzone'

const UserProfiles = () => {

  const [userProfiles, setUserProfiles] = useState([]);

  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/api/v1/user-profile").then(res => {
      console.log(res);
      setUserProfiles(res.data);
    });
  };

  useEffect(() => { 
    fetchUserProfiles();
  }, []);

  return userProfiles.map((userProfile, index) => {
    return (
      <div key={index}>
        {
          userProfile.userId ? 
          <img src={`http://localhost:8080/api/v1/user-profile/${userProfile.userId}/image/download`} /> : 
          null
        }
        <Dropzone {...userProfile} />
        <h1>{userProfile.userName}</h1>
        <p>{userProfile.userId}</p>
      </div>
    );
  });
};

function Dropzone({userId}) {
  const onDrop = useCallback(acceptedFiles => {
    const image = acceptedFiles[0];
    console.log(image);
    const formData = new FormData();
    formData.append("file", image);

    axios.post(`http://localhost:8080/api/v1/user-profile/${userId}/image/upload`, 
      formData, 
      { 
        headers: {
        "content-Type": "multipart/form-data"
        }
      }
    ).then(res => {
      console.log("Image uploaded successfully. ", res);
    }).catch(err => {
      console.log("Image uploaded unsuccessfully. ", err);
    });
  }, []);
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the image here ...</p> :
          <p>Drag 'n' drop some files here, or click to select files</p>
      }
    </div>
  )
}

function App() {
  return (
    <div className="App">
      <UserProfiles />
    </div>
  );
}

export default App;

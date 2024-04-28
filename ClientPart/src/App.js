import React from 'react';
import LanguageDetectionForm from './components/LanguageDetectionForm';
import DeleteTextForm from './components/DeleteTextForm';
import PostTextForm from './components/PostTextForm';
import UpdateTextForm from './components/UpdateTextForm';

function App() {
    return (
        <div>
            <h1><center>Language Detection</center></h1>
            <LanguageDetectionForm />
            <PostTextForm />
            <DeleteTextForm />
            <UpdateTextForm />
        </div>
    );
}

export default App;

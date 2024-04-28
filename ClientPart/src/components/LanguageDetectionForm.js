import React, { useState } from 'react';
import axios from 'axios';
import styles from './LanguageDetectionForm.module.css';

const LanguageDetectionForm = () => {
    const [text, setText] = useState('');
    const [response, setResponse] = useState('');
    const [detectedLanguage, setDetectedLanguage] = useState('');

    const handleInputChange = (e) => {
        setText(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const res = await axios.get('http://localhost:8080/api/v1/detect-language', {
                params: {
                    text: text,
                },
            });

            // Обработка ответа сервера
            setResponse(res.data.message);
            setDetectedLanguage(res.data.language);
        } catch (error) {
            console.error('Error detecting language:', error);
            setResponse('Error detecting language');
        }
    };

    return (
        <div className={styles.formContainer}>
            <form onSubmit={handleSubmit}>
                <label className={styles.formLabel}>
                    Enter text:
                    <input
                        type="text"
                        value={text}
                        onChange={handleInputChange}
                        className={styles.formInput}
                    />
                </label>
                <button type="submit" className={styles.submitButton}>
                    Detect Language
                </button>
            </form>
            {response && (
                <div className={styles.response}>
                    <p>Response: {response}</p>
                    {detectedLanguage && (
                        <p>Detected Language: {detectedLanguage}</p>
                    )}
                </div>
            )}
        </div>
    );
};

export default LanguageDetectionForm;

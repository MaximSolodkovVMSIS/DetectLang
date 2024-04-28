import React, { useState } from 'react';
import axios from 'axios';
import styles from './PostTextForm.module.css';

const PostTextForm = () => {
    const [texts, setTexts] = useState([]);
    const [response, setResponse] = useState([]);

    const handleTextChange = (e) => {
        const inputText = e.target.value;
        const textArray = inputText.split('\n');
        setTexts(textArray);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const res = await axios.post('http://localhost:8080/api/v1/text/bulk', {
                parameters: texts,
            });

            setResponse(res.data);
        } catch (error) {
            console.error('Error adding texts:', error);
            setResponse([{ message: 'Error adding texts' }]);
        }
    };

    return (
        <div className={styles.formContainer}>
            <form onSubmit={handleSubmit}>
                <label className={styles.formLabel}>
                    Enter texts (separated by new lines):
                    <textarea
                        value={texts.join('\n')}
                        onChange={handleTextChange}
                        className={styles.formTextarea}
                    />
                </label>
                <button type="submit" className={styles.submitButton}>
                    Add Texts
                </button>
            </form>
            <div className={styles.responseContainer}>
                {response.map((res, index) => (
                    <p key={index} className={styles.response}>
                        Response: {res.message}
                        <br />
                        {res.text} - {res.language}
                    </p>
                ))}
            </div>
        </div>
    );
};

export default PostTextForm;

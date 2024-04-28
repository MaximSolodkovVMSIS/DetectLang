import React, { useState } from 'react';
import axios from 'axios';
import styles from './UpdateTextForm.module.css';

const UpdateTextForm = () => {
    const [id, setId] = useState('');
    const [newText, setNewText] = useState('');
    const [response, setResponse] = useState('');

    const handleIdChange = (e) => {
        setId(e.target.value);
    };

    const handleNewTextChange = (e) => {
        setNewText(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const res = await axios.put(`http://localhost:8080/api/v1/text/${id}`, {
                newText: newText,
            });

            setResponse(res.data);
        } catch (error) {
            console.error('Error updating text:', error);
            setResponse('Error updating text');
        }
    };

    return (
        <div className={styles.formContainer}>
            <form onSubmit={handleSubmit}>
                <label className={styles.formLabel}>
                    Enter ID of text to update:
                    <input
                        type="text"
                        value={id}
                        onChange={handleIdChange}
                        className={styles.formInput}
                    />
                </label>
                <label className={styles.formLabel}>
                    Enter new text:
                    <input
                        type="text"
                        value={newText}
                        onChange={handleNewTextChange}
                        className={styles.formInput}
                    />
                </label>
                <button type="submit" className={styles.submitButton}>
                    Update Text
                </button>
            </form>
            {response && (
                <p className={styles.response}>Response: {response}</p>
            )}
        </div>
    );
};

export default UpdateTextForm;

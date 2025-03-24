package org.kylecodes.gm.services;

import org.springframework.stereotype.Service;

@Service
public class JwtService {
    public String generateToken() {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6ImVARS5jb20iLCJhZG1pbiI6dHJ1ZSwiaWF0IjoxNTE2MjM5MDIyfQ.QcgfQ1bHltQbVwKscZaWx3byB6kyzgcxvg0-tcQ3u4k";
    }
}

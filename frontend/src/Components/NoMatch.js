import * as React from "react";
import { Link } from "react-router-dom";

export default function NoMatch() {
  return (
    <div>
      <h2>Invalid page</h2>
      <p>
        <Link to="/">Back to the main page</Link>
      </p>
    </div>
  );
}
